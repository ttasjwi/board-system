package com.ttasjwi.board.system.member.domain.policy.fixture

import com.ttasjwi.board.system.common.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*


@DisplayName("NicknamePolicyFixture 테스트")
class NicknamePolicyFixtureTest {

    private lateinit var nicknamePolicyFixture: NicknamePolicyFixture

    @BeforeEach
    fun setup() {
        this.nicknamePolicyFixture = NicknamePolicyFixture()
    }

    @Nested
    @DisplayName("validate : 닉네임 문자열을 검증하고 그 결과를 Result 로 담아 반환한다.")
    inner class Validate {

        @Test
        @DisplayName("성공하면 그 닉네임 문자열을 Result 에 담아 반환한다.")
        fun testSuccess() {
            val value = "땃쥐"

            val result = nicknamePolicyFixture.validate(value)
            val nickname = result.getOrThrow()

            assertThat(result.isSuccess).isTrue()
            assertThat(nickname).isEqualTo(value)
        }

        @Test
        @DisplayName("ERROR_NICKNAME 을 전달하면 실패한다.")
        fun testFailure() {
            val value = NicknamePolicyFixture.ERROR_NICKNAME

            val result = nicknamePolicyFixture.validate(value)
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
            val nickname = nicknamePolicyFixture.createRandom()

            assertThat(nickname).isEqualTo(nicknamePolicyFixture.randomNames[0])
            assertThat(nicknamePolicyFixture.randomNameCursor).isEqualTo(1)
        }

        @Test
        @DisplayName("2번째 호출하면 1번째 인덱스 요소의 닉네임이 생성됨")
        fun test2() {
            nicknamePolicyFixture.createRandom()
            val nickname = nicknamePolicyFixture.createRandom()

            assertThat(nickname).isEqualTo(nicknamePolicyFixture.randomNames[1])
            assertThat(nicknamePolicyFixture.randomNameCursor).isEqualTo(0)
        }

        @Test
        @DisplayName("3번째 호출하면 0번째 인덱스 요소의 닉네임이 생성됨")
        fun test3() {
            nicknamePolicyFixture.createRandom()
            nicknamePolicyFixture.createRandom()
            val nickname = nicknamePolicyFixture.createRandom()

            assertThat(nickname).isEqualTo(nicknamePolicyFixture.randomNames[0])
            assertThat(nicknamePolicyFixture.randomNameCursor).isEqualTo(1)
        }
    }
}
