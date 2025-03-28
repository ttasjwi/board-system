package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.member.domain.service.EmailCreator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("EmailCreatorFixture 테스트")
class EmailCreatorFixtureTest {

    private lateinit var emailCreator: EmailCreator

    @BeforeEach
    fun setup() {
        emailCreator = EmailCreatorFixture()
    }

    @Nested
    @DisplayName("createEmail: 문자열로 이메일을 생성하고 그 결과가 담긴 Result 를 반환한다.")
    inner class CreateEmail {

        @Test
        @DisplayName("정상적인 이메일 문자열이 전달되면 이메일이 담긴 Result가 반환된다.")
        fun createSuccess() {
            val value = "hello@gmail.com"

            val result = emailCreator.create(value)
            val email = result.getOrThrow()

            assertThat(result.isSuccess).isTrue()
            assertThat(email).isNotNull
            assertThat(email.value).isEqualTo(value)
        }

        @Test
        @DisplayName("이메일 문자열이 ERROR_EMAIL 이면 예외가 담긴 실패 Result 를 반환한다.")
        fun createFailure() {
            val value = EmailCreatorFixture.ERROR_EMAIL

            val result = emailCreator.create(value)
            val exception = assertThrows<CustomException> { result.getOrThrow() }

            assertThat(result.isFailure).isTrue()
            assertThat(exception.debugMessage).isEqualTo("이메일 포맷 예외 - 픽스쳐")
        }
    }
}
