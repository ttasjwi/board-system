package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.common.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("UsernameCreatorFixture: 사용자 아이디(username) 생성기 픽스쳐")
class UsernameCreatorFixtureTest {


    private lateinit var usernameCreatorFixture: UsernameCreatorFixture

    @BeforeEach
    fun setup() {
        this.usernameCreatorFixture = UsernameCreatorFixture()
    }

    @Nested
    @DisplayName("create : 문자열로부터 Username 인스턴스를 생성하고 그 결과를 Result 로 담아 반환한다.")
    inner class Create {


        @Test
        @DisplayName("성공하면 그 값을 가진 Username 을 Result 에 담아 반환한다.")
        fun testSuccess() {
            val value = "test"

            val result = usernameCreatorFixture.create(value)
            val username = result.getOrThrow()

            assertThat(result.isSuccess).isTrue()
            assertThat(username.value).isEqualTo(value)
        }

        @Test
        @DisplayName("ERROR_USERNAME 을 전달하면 실패한다.")
        fun testFailure() {
            val value = UsernameCreatorFixture.ERROR_USERNAME

            val result = usernameCreatorFixture.create(value)
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
            val username = usernameCreatorFixture.createRandom()

            assertThat(username.value).isEqualTo(usernameCreatorFixture.randomNames[0])
            assertThat(usernameCreatorFixture.randomNameCursor).isEqualTo(1)
        }

        @Test
        @DisplayName("2번째 호출하면 1번째 인덱스 요소의 username이 생성됨")
        fun test2() {
            usernameCreatorFixture.createRandom()
            val username = usernameCreatorFixture.createRandom()

            assertThat(username.value).isEqualTo(usernameCreatorFixture.randomNames[1])
            assertThat(usernameCreatorFixture.randomNameCursor).isEqualTo(0)
        }

        @Test
        @DisplayName("3번째 호출하면 0번째 인덱스 요소의 username이 생성됨")
        fun test3() {
            usernameCreatorFixture.createRandom()
            usernameCreatorFixture.createRandom()
            val username = usernameCreatorFixture.createRandom()

            assertThat(username.value).isEqualTo(usernameCreatorFixture.randomNames[0])
            assertThat(usernameCreatorFixture.randomNameCursor).isEqualTo(1)
        }
    }
}
