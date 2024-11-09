package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.exception.EmailVerificationExpiredException
import com.ttasjwi.board.system.member.domain.exception.InvalidEmailVerificationCodeException
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.member.domain.service.EmailVerificationHandler
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

class EmailVerificationHandlerImplTest {

    private lateinit var emailVerificationHandler: EmailVerificationHandler

    @BeforeEach
    fun setup() {
        emailVerificationHandler = EmailVerificationHandlerImpl()
    }

    @Nested
    @DisplayName("CoeVerify: 코드를 통해 이메일 인증을 처리한다")
    inner class CodeVerify {

        @Test
        @DisplayName("코드 만료시각과 같은 시각에 인증을 시도하면 실패한다")
        fun test1() {
            val emailVerification = emailVerificationFixtureNotVerified(
                email = "hello@gmail.com",
                code = "1111",
                codeCreatedAt = timeFixture(minute = 0),
                codeExpiresAt = timeFixture(minute = 5)
            )

            assertThrows<EmailVerificationExpiredException> {
                emailVerificationHandler.codeVerify(emailVerification, "1111", timeFixture(minute = 5))
            }
        }

        @Test
        @DisplayName("코드 만료시간 이후에 인증을 시도하면 실패한다")
        fun test2() {
            val emailVerification = emailVerificationFixtureNotVerified(
                email = "hello@gmail.com",
                code = "1111",
                codeCreatedAt = timeFixture(minute = 0),
                codeExpiresAt = timeFixture(minute = 5)
            )
            assertThrows<EmailVerificationExpiredException> {
                emailVerificationHandler.codeVerify(emailVerification, "1111", timeFixture(minute = 6))
            }
        }

        @Test
        @DisplayName("코드가 잘못됐다면 예외가 발생한다")
        fun test3() {
            val emailVerification = emailVerificationFixtureNotVerified(
                email = "hello@gmail.com",
                code = "1111",
                codeCreatedAt = timeFixture(minute = 0),
                codeExpiresAt = timeFixture(minute = 5)
            )
            assertThrows<InvalidEmailVerificationCodeException> {
                emailVerificationHandler.codeVerify(emailVerification, "1112", timeFixture(minute = 3))
            }
        }

        @Test
        @DisplayName(
            "인증 성공 후, 인증 시각 및 인증 만료시각 정보를 가지며" +
                    "인증 만료시각은 인증시각으로부터 ${EmailVerification.VERIFICATION_VALIDITY_MINUTE} 분 뒤까지이다."
        )
        fun test4() {
            val emailVerification = emailVerificationFixtureNotVerified(
                email = "hello@gmail.com",
                code = "1111",
                codeCreatedAt = timeFixture(minute = 0),
                codeExpiresAt = timeFixture(minute = 5)
            )

            emailVerificationHandler.codeVerify(emailVerification, "1111", timeFixture(minute = 3))

            assertThat(emailVerification.verifiedAt).isEqualTo(timeFixture(minute = 3))
            assertThat(emailVerification.verificationExpiresAt).isEqualTo(timeFixture(minute = 3).plusMinutes(EmailVerification.VERIFICATION_VALIDITY_MINUTE))
        }
    }
}
