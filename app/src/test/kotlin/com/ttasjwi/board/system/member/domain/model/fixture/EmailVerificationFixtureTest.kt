package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("EmailVerification Fixture 테스트")
class EmailVerificationFixtureTest {

    @Nested
    @DisplayName("emailVerificationFixtureNotVerified() : 검증되지 않은 이메일 인증 인스턴스 생성")
    inner class NotVerified {

        @Test
        @DisplayName("인자가 없어도 미인증 상태의 EmailVerification 을 생성하며, 기본값이 부여된다.")
        fun test() {
            val emailVerification = emailVerificationFixtureNotVerified()
            assertThat(emailVerification.email).isNotNull
            assertThat(emailVerification.code).isNotNull
            assertThat(emailVerification.codeCreatedAt).isNotNull
            assertThat(emailVerification.codeExpiresAt).isNotNull
            assertThat(emailVerification.verifiedAt).isNull()
            assertThat(emailVerification.verificationExpiresAt).isNull()
        }

        @Test
        @DisplayName("인자를 지정하면서 미인증 상태의 EmailVerification 을 생성할 수 있다.")
        fun test2() {
            val email = "hello@gmail.com"
            val code = "12357dadf54"
            val codeCreatedAt = appDateTimeFixture(minute = 0)
            val codeExpiresAt = appDateTimeFixture(minute = 5)
            val emailVerification = emailVerificationFixtureNotVerified(
                email = email,
                code = code,
                codeCreatedAt = codeCreatedAt,
                codeExpiresAt = codeExpiresAt
            )
            assertThat(emailVerification.email).isEqualTo(emailFixture(email))
            assertThat(emailVerification.code).isEqualTo(code)
            assertThat(emailVerification.codeCreatedAt).isEqualTo(codeCreatedAt)
            assertThat(emailVerification.codeExpiresAt).isEqualTo(codeExpiresAt)
            assertThat(emailVerification.verifiedAt).isNull()
            assertThat(emailVerification.verificationExpiresAt).isNull()
        }
    }

    @Nested
    @DisplayName("emailVerificationFixtureVerified() : 검증된 이메일 인증 인스턴스 생성")
    inner class Verified {

        @Test
        @DisplayName("인자가 없어도 인증 상태의 EmailVerification 을 생성하며, 기본값이 부여된다.")
        fun test() {
            val emailVerification = emailVerificationFixtureVerified()
            assertThat(emailVerification.email).isNotNull
            assertThat(emailVerification.code).isNotNull
            assertThat(emailVerification.codeCreatedAt).isNotNull
            assertThat(emailVerification.codeExpiresAt).isNotNull
            assertThat(emailVerification.verifiedAt).isNotNull()
            assertThat(emailVerification.verificationExpiresAt).isNotNull()
        }

        @Test
        @DisplayName("인자를 지정하면서 인증 상태의 EmailVerification 을 생성할 수 있다.")
        fun test2() {
            val email = "hello@gmail.com"
            val code = "12357dadf54"
            val codeCreatedAt = appDateTimeFixture(minute = 0)
            val codeExpiresAt = appDateTimeFixture(minute = 5)
            val verifiedAt = appDateTimeFixture(minute = 1)
            val verificationExpiresAt = appDateTimeFixture(minute = 31)
            val emailVerification = emailVerificationFixtureVerified(
                email = email,
                code = code,
                codeCreatedAt = codeCreatedAt,
                codeExpiresAt = codeExpiresAt,
                verifiedAt = verifiedAt,
                verificationExpiresAt = verificationExpiresAt
            )
            assertThat(emailVerification.email).isEqualTo(emailFixture(email))
            assertThat(emailVerification.code).isEqualTo(code)
            assertThat(emailVerification.codeCreatedAt).isEqualTo(codeCreatedAt)
            assertThat(emailVerification.codeExpiresAt).isEqualTo(codeExpiresAt)
            assertThat(emailVerification.verifiedAt).isEqualTo(verifiedAt)
            assertThat(emailVerification.verificationExpiresAt).isEqualTo(verificationExpiresAt)
        }
    }
}
