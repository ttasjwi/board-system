package com.ttasjwi.board.system.user.domain.model

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.exception.EmailNotVerifiedException
import com.ttasjwi.board.system.user.domain.exception.EmailVerificationExpiredException
import com.ttasjwi.board.system.user.domain.exception.InvalidEmailVerificationCodeException
import com.ttasjwi.board.system.user.domain.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.user.domain.model.fixture.emailVerificationFixtureVerified
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("EmailVerification 테스트")
class EmailVerificationTest {

    @Nested
    @DisplayName("create: 이메일 인증을 생성한다.")
    inner class Create {

        @Test
        @DisplayName("생성하면 랜덤한 코드를 가진 이메일 인증이 생성된다.")
        fun testCode() {
            val email = "soso@gmail.com"
            val currentTime = appDateTimeFixture(minute = 0)

            val emailVerification = EmailVerification.create(
                email = email,
                currentTime = currentTime
            )
            assertThat(emailVerification.email).isEqualTo(email)
            assertThat(emailVerification.code.length).isEqualTo(EmailVerification.CODE_LENGTH)
            assertThat(emailVerification.codeCreatedAt).isEqualTo(currentTime)
            assertThat(emailVerification.codeExpiresAt).isEqualTo(currentTime.plusMinutes(EmailVerification.CODE_VALIDITY_MINUTE))
            assertThat(emailVerification.verifiedAt).isNull()
            assertThat(emailVerification.verificationExpiresAt).isNull()
        }
    }

    @Nested
    @DisplayName("restore: 값으로부터 이메일 인증 인스턴스를 복원한다.")
    inner class Restore {

        @Test
        @DisplayName("전달된 값으로 인스턴스를 성공적으로 복원해야한다.")
        fun test() {
            val email = "nyaru@gmail.com"
            val code = "ads7fa778"
            val codeCreatedAt = appDateTimeFixture(minute = 3).toInstant()
            val codeExpiresAt = appDateTimeFixture(minute = 8).toInstant()
            val verifiedAt = appDateTimeFixture(minute = 5).toInstant()
            val verificationExpiresAt = appDateTimeFixture(minute = 35).toInstant()

            val emailVerification = EmailVerification.restore(
                email = email,
                code = code,
                codeCreatedAt = codeCreatedAt,
                codeExpiresAt = codeExpiresAt,
                verifiedAt = verifiedAt,
                verificationExpiresAt = verificationExpiresAt
            )

            assertThat(emailVerification.email).isEqualTo(email)
            assertThat(emailVerification.code).isEqualTo(code)
            assertThat(emailVerification.codeCreatedAt.toInstant()).isEqualTo(codeCreatedAt)
            assertThat(emailVerification.codeExpiresAt.toInstant()).isEqualTo(codeExpiresAt)
            assertThat(emailVerification.verifiedAt!!.toInstant()).isEqualTo(verifiedAt)
            assertThat(emailVerification.verificationExpiresAt!!.toInstant()).isEqualTo(verificationExpiresAt)
        }
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
                emailVerification.codeVerify("1111", appDateTimeFixture(minute = 5))
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
                emailVerification.codeVerify("1111", appDateTimeFixture(minute = 6))
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
                emailVerification.codeVerify("1112", appDateTimeFixture(minute = 3))
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

            val verifiedEmailVerification = emailVerification.codeVerify("1111", appDateTimeFixture(minute = 3))

            assertThat(verifiedEmailVerification.verifiedAt).isEqualTo(appDateTimeFixture(minute = 3))
            assertThat(verifiedEmailVerification.verificationExpiresAt).isEqualTo(
                appDateTimeFixture(minute = 3).plusMinutes(
                    EmailVerification.VERIFICATION_VALIDITY_MINUTE
                )
            )
        }
    }

    @Nested
    @DisplayName("throwIfNotVerifiedOrCurrentlyNotValid: 이메일 인증이 처리됐는 지, 현재 유효한 지 확인후 실패하면 예외 발생")
    inner class ThrowIfNotVerifiedOrCurrentlyNotValid {

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
            emailVerification.throwIfNotVerifiedOrCurrentlyNotValid(currentTime)
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
                emailVerification.throwIfNotVerifiedOrCurrentlyNotValid(currentTime)
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
                emailVerification.throwIfNotVerifiedOrCurrentlyNotValid(currentTime)
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
                emailVerification.throwIfNotVerifiedOrCurrentlyNotValid(currentTime)
            }
        }
    }
}
