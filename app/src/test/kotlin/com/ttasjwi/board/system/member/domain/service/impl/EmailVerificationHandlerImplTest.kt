package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.exception.EmailNotVerifiedException
import com.ttasjwi.board.system.member.domain.exception.EmailVerificationExpiredException
import com.ttasjwi.board.system.member.domain.exception.InvalidEmailVerificationCodeException
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureVerified
import com.ttasjwi.board.system.member.domain.service.EmailVerificationHandler
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("EmailVerificationHandlerImpl: 이메일 인증 처리기")
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
                codeCreatedAt = appDateTimeFixture(minute = 0),
                codeExpiresAt = appDateTimeFixture(minute = 5)
            )

            assertThrows<EmailVerificationExpiredException> {
                emailVerificationHandler.codeVerify(emailVerification, "1111", appDateTimeFixture(minute = 5))
            }
        }

        @Test
        @DisplayName("코드 만료시간 이후에 인증을 시도하면 실패한다")
        fun test2() {
            val emailVerification = emailVerificationFixtureNotVerified(
                email = "hello@gmail.com",
                code = "1111",
                codeCreatedAt = appDateTimeFixture(minute = 0),
                codeExpiresAt = appDateTimeFixture(minute = 5)
            )
            assertThrows<EmailVerificationExpiredException> {
                emailVerificationHandler.codeVerify(emailVerification, "1111", appDateTimeFixture(minute = 6))
            }
        }

        @Test
        @DisplayName("코드가 잘못됐다면 예외가 발생한다")
        fun test3() {
            val emailVerification = emailVerificationFixtureNotVerified(
                email = "hello@gmail.com",
                code = "1111",
                codeCreatedAt = appDateTimeFixture(minute = 0),
                codeExpiresAt = appDateTimeFixture(minute = 5)
            )
            assertThrows<InvalidEmailVerificationCodeException> {
                emailVerificationHandler.codeVerify(emailVerification, "1112", appDateTimeFixture(minute = 3))
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
                codeCreatedAt = appDateTimeFixture(minute = 0),
                codeExpiresAt = appDateTimeFixture(minute = 5)
            )

            emailVerificationHandler.codeVerify(emailVerification, "1111", appDateTimeFixture(minute = 3))

            assertThat(emailVerification.verifiedAt).isEqualTo(appDateTimeFixture(minute = 3))
            assertThat(emailVerification.verificationExpiresAt).isEqualTo(
                appDateTimeFixture(minute = 3).plusMinutes(
                    EmailVerification.VERIFICATION_VALIDITY_MINUTE
                )
            )
        }
    }

    @Nested
    @DisplayName("checkVerifiedAndCurrentlyValid: 이메일 인증이 처리됐는 지, 현재 유효한 지 체크")
    inner class CheckVerifiedAndCurrentlyValid {

        @Test
        @DisplayName("성공 - 인증이 됐고, 현재 시간이 만료 시간 이전일 경우")
        fun testSuccess() {
            val emailVerification = emailVerificationFixtureVerified(
                codeCreatedAt = appDateTimeFixture(minute = 3),
                codeExpiresAt = appDateTimeFixture(minute = 8),
                verifiedAt = appDateTimeFixture(minute = 7),
                verificationExpiresAt = appDateTimeFixture(minute = 37),
            )
            val currentTime = appDateTimeFixture(minute = 34)
            emailVerificationHandler.checkVerifiedAndCurrentlyValid(emailVerification, currentTime)
        }

        @Test
        @DisplayName("인증되지 않았다면 예외가 발생한다")
        fun testNotVerified() {
            val emailVerification = emailVerificationFixtureNotVerified(
                codeCreatedAt = appDateTimeFixture(minute = 3),
                codeExpiresAt = appDateTimeFixture(minute = 8)
            )
            val currentTime = appDateTimeFixture(minute = 7)
            assertThrows<EmailNotVerifiedException> {
                emailVerificationHandler.checkVerifiedAndCurrentlyValid(emailVerification, currentTime)
            }
        }

        @Test
        @DisplayName("인증이 됐지만, 현재 시간이 만료 시간과 같으면 예외가 발생한다")
        fun testExpired1() {
            val emailVerification = emailVerificationFixtureVerified(
                codeCreatedAt = appDateTimeFixture(minute = 3),
                codeExpiresAt = appDateTimeFixture(minute = 8),
                verifiedAt = appDateTimeFixture(minute = 7),
                verificationExpiresAt = appDateTimeFixture(minute = 37),
            )
            val currentTime = appDateTimeFixture(minute = 37)
            assertThrows<EmailVerificationExpiredException> {
                emailVerificationHandler.checkVerifiedAndCurrentlyValid(emailVerification, currentTime)
            }
        }

        @Test
        @DisplayName("인증이 됐지만, 현재 시간이 만료 시간 이후일 경우 예외가 발생한다")
        fun testExpired2() {
            val emailVerification = emailVerificationFixtureVerified(
                codeCreatedAt = appDateTimeFixture(minute = 3),
                codeExpiresAt = appDateTimeFixture(minute = 8),
                verifiedAt = appDateTimeFixture(minute = 7),
                verificationExpiresAt = appDateTimeFixture(minute = 37),
            )
            val currentTime = appDateTimeFixture(minute = 38)
            assertThrows<EmailVerificationExpiredException> {
                emailVerificationHandler.checkVerifiedAndCurrentlyValid(emailVerification, currentTime)
            }
        }
    }
}
