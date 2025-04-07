package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.global.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*


@DisplayName("NicknameManagerFixture: 닉네임 관련 정책, 기능을 책임지는 도메인 서비스 픽스쳐")
class NicknameManagerFixtureTest {

    private lateinit var nicknameManagerFixture: NicknameManagerFixture

    @BeforeEach
    fun setup() {
        this.nicknameManagerFixture = NicknameManagerFixture()
    }

    @Nested
    @DisplayName("validate : 닉네임 문자열을 검증하고 그 결과를 Result 로 담아 반환한다.")
    inner class Validate {

        @Test
        @DisplayName("성공하면 그 닉네임 문자열을 Result 에 담아 반환한다.")
        fun testSuccess() {
            val value = "땃쥐"

            val result = nicknameManagerFixture.validate(value)
            val nickname = result.getOrThrow()

            assertThat(result.isSuccess).isTrue()
            assertThat(nickname).isEqualTo(value)
        }

        @Test
        @DisplayName("ERROR_NICKNAME 을 전달하면 실패한다.")
        fun testFailure() {
            val value = NicknameManagerFixture.ERROR_NICKNAME

            val result = nicknameManagerFixture.validate(value)
            val exception = assertThrows<CustomException> { result.getOrThrow() }


            assertThat(result.isFailure).isTrue()
            assertThat(exception.debugMessage).isEqualTo("닉네임 포맷 예외 - 픽스쳐")
        }
    }

    @Nested
    @DisplayName("createRandom : 호출하면 정해진 리스트 내의 닉네임을 순회하며 생성한다.")
    inner class CreateRandom {

        @Test
        @DisplayName("최초 호출하면 0번째 인덱스 요소의 닉네임이 생성됨")
        fun test1() {
            val nickname = nicknameManagerFixture.createRandom()

            assertThat(nickname).isEqualTo(nicknameManagerFixture.randomNames[0])
            assertThat(nicknameManagerFixture.randomNameCursor).isEqualTo(1)
        }

        @Test
        @DisplayName("2번째 호출하면 1번째 인덱스 요소의 닉네임이 생성됨")
        fun test2() {
            nicknameManagerFixture.createRandom()
            val nickname = nicknameManagerFixture.createRandom()

            assertThat(nickname).isEqualTo(nicknameManagerFixture.randomNames[1])
            assertThat(nicknameManagerFixture.randomNameCursor).isEqualTo(0)
        }

        @Test
        @DisplayName("3번째 호출하면 0번째 인덱스 요소의 닉네임이 생성됨")
        fun test3() {
            nicknameManagerFixture.createRandom()
            nicknameManagerFixture.createRandom()
            val nickname = nicknameManagerFixture.createRandom()

            assertThat(nickname).isEqualTo(nicknameManagerFixture.randomNames[0])
            assertThat(nicknameManagerFixture.randomNameCursor).isEqualTo(1)
        }
    }
}
