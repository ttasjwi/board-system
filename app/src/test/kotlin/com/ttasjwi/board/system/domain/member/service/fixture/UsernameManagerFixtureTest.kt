package com.ttasjwi.board.system.domain.member.service.fixture

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.domain.member.service.fixture.UsernameManagerFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("UsernameManagerFixture: 사용자아이디(username) 관련 정책, 기능을 책임지는 도메인 서비스 픽스쳐")
class UsernameManagerFixtureTest {


    private lateinit var usernameManagerFixture: UsernameManagerFixture

    @BeforeEach
    fun setup() {
        this.usernameManagerFixture = UsernameManagerFixture()
    }

    @Nested
    @DisplayName("validate() : 사용자 아이디를 검증하고, 그 결과를 Result 로 담아 반환한다.")
    inner class Validate {

        @Test
        @DisplayName("성공하면 그 값을 가진 Username 을 Result 에 담아 반환한다.")
        fun testSuccess() {
            val value = "test"

            val result = usernameManagerFixture.validate(value)
            val username = result.getOrThrow()

            assertThat(result.isSuccess).isTrue()
            assertThat(username).isEqualTo(value)
        }

        @Test
        @DisplayName("ERROR_USERNAME 을 전달하면 실패한다.")
        fun testFailure() {
            val value = UsernameManagerFixture.ERROR_USERNAME

            val result = usernameManagerFixture.validate(value)
            val exception = assertThrows<CustomException> { result.getOrThrow() }


            assertThat(result.isFailure).isTrue()
            assertThat(exception.debugMessage).isEqualTo("사용자아이디(username) 포맷 예외 - 픽스쳐")
        }
    }

    @Nested
    @DisplayName("createRandom : 호출하면 정해진 리스트 내의 username 을 순회하며 생성한다.")
    inner class CreateRandom {

        @Test
        @DisplayName("최초 호출하면 0번째 인덱스 요소의 username이 생성됨")
        fun test1() {
            val username = usernameManagerFixture.createRandom()

            assertThat(username).isEqualTo(usernameManagerFixture.randomNames[0])
            assertThat(usernameManagerFixture.randomNameCursor).isEqualTo(1)
        }

        @Test
        @DisplayName("2번째 호출하면 1번째 인덱스 요소의 username이 생성됨")
        fun test2() {
            usernameManagerFixture.createRandom()
            val username = usernameManagerFixture.createRandom()

            assertThat(username).isEqualTo(usernameManagerFixture.randomNames[1])
            assertThat(usernameManagerFixture.randomNameCursor).isEqualTo(0)
        }

        @Test
        @DisplayName("3번째 호출하면 0번째 인덱스 요소의 username이 생성됨")
        fun test3() {
            usernameManagerFixture.createRandom()
            usernameManagerFixture.createRandom()
            val username = usernameManagerFixture.createRandom()

            assertThat(username).isEqualTo(usernameManagerFixture.randomNames[0])
            assertThat(usernameManagerFixture.randomNameCursor).isEqualTo(1)
        }
    }
}
