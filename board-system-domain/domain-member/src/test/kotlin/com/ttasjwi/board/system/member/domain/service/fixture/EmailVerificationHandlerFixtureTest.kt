package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureNotVerified
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class EmailVerificationHandlerFixtureTest {

    private lateinit var emailVerificationHandlerFixture: EmailVerificationHandlerFixture

    @BeforeEach
    fun setup() {
        emailVerificationHandlerFixture = EmailVerificationHandlerFixture()
    }

    @Nested
    inner class CodeVerify {

        @Test
        @DisplayName("이메일 인증을 생성하며, " +
                "이메일 인증의 만료시점은 인증시각 기준 ${EmailVerificationHandlerFixture.VERIFICATION_VALIDITY_MINUTE} 분 뒤까지이다.")
        fun test() {
            val emailVerification = emailVerificationFixtureNotVerified()
            val currentTime = timeFixture(minute=3)

            val verifiedEmailVerification = emailVerificationHandlerFixture.codeVerify(emailVerification, emailVerification.code, currentTime)

            assertThat(verifiedEmailVerification.verifiedAt).isEqualTo(currentTime)
            assertThat(verifiedEmailVerification.verificationExpiresAt).isEqualTo(currentTime.plusMinutes(EmailVerificationHandlerFixture.VERIFICATION_VALIDITY_MINUTE))
        }

    }
}
