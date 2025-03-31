package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.member.domain.exception.InvalidEmailFormatException
import com.ttasjwi.board.system.member.domain.external.ExternalEmailFormatChecker
import com.ttasjwi.board.system.member.domain.external.fixture.ExternalEmailFormatCheckerFixture
import com.ttasjwi.board.system.member.domain.model.fixture.emailFixture
import com.ttasjwi.board.system.member.domain.service.EmailCreator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("EmailCreatorImpl 테스트")
class EmailCreatorImplTest {


    private lateinit var emailCreator: EmailCreator
    private lateinit var externalEmailFormatChecker: ExternalEmailFormatChecker

    @BeforeEach
    fun setup() {
        externalEmailFormatChecker = ExternalEmailFormatCheckerFixture()
        emailCreator = EmailCreatorImpl(externalEmailFormatChecker)
    }

    @Nested
    @DisplayName("create: 이메일을 생성하고 Result 에 담아 보낸다. 실패하면 예외 발생")
    inner class Create {

        @DisplayName("이메일 포맷이 올바르지 않으면 예외가 발생하여 Result.Failure 가 반환된다.")
        @Test
        fun testFailure() {
            val emailValue = ExternalEmailFormatCheckerFixture.INVALID_FORMAT_EMAIL

            val result = emailCreator.create(emailValue)
            val exception = assertThrows<InvalidEmailFormatException> {
                result.getOrThrow()
            }

            assertThat(result.isFailure).isTrue()
            assertThat(exception).isInstanceOf(InvalidEmailFormatException::class.java)
            assertThat(exception.args[0]).isEqualTo(emailValue)
        }

        @DisplayName("이메일 포맷이 올바르면 Result.Success 에 이메일이 담겨 반환된다.")
        @Test
        fun testSuccess() {
            val emailValue = "test@gmail.com"

            val result = emailCreator.create(emailValue)
            val emailDomain = result.getOrThrow()

            assertThat(result.isSuccess).isTrue()
            assertThat(emailDomain).isNotNull
            assertThat(emailDomain).isEqualTo(emailFixture(emailValue))
        }
    }
}
