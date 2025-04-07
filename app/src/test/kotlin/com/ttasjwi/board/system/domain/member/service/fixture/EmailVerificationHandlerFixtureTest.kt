package com.ttasjwi.board.system.domain.member.service.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.domain.member.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.domain.member.model.fixture.emailVerificationFixtureVerified
import com.ttasjwi.board.system.domain.member.service.fixture.EmailVerificationHandlerFixture
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationHandlerFixture 테스트")
class EmailVerificationHandlerFixtureTest {

    private lateinit var emailVerificationHandlerFixture: EmailVerificationHandlerFixture

    @BeforeEach
    fun setup() {
        emailVerificationHandlerFixture = EmailVerificationHandlerFixture()
    }

    @Nested
    @DisplayName("codeVerify: 코드를 받아 이메일 인증을 처리한다.")
    inner class CodeVerify {

        @Test
        @DisplayName("이메일 인증을 생성하며, " +
                "이메일 인증의 만료시점은 인증시각 기준 ${EmailVerificationHandlerFixture.VERIFICATION_VALIDITY_MINUTE} 분 뒤까지이다.")
        fun test() {
            val emailVerification = emailVerificationFixtureNotVerified()
            val currentTime = appDateTimeFixture(minute=3)

            val verifiedEmailVerification = emailVerificationHandlerFixture.codeVerify(emailVerification, emailVerification.code, currentTime)

            assertThat(verifiedEmailVerification.verifiedAt).isEqualTo(currentTime)
            assertThat(verifiedEmailVerification.verificationExpiresAt).isEqualTo(currentTime.plusMinutes(
                EmailVerificationHandlerFixture.VERIFICATION_VALIDITY_MINUTE))
        }
    }

    @Nested
    @DisplayName("checkVerifiedAndCurrentlyValid: 이메일 인증이 인증 됐는지, 현재 인증이 유효한 지 체크한다")
    inner class CheckVerifiedAndCurrentlyValid {

        @Test
        @DisplayName("픽스쳐 - 아무 것도 안 한다. 실행만 되는 지 테스트")
        fun test() {
            val emailVerification = emailVerificationFixtureVerified()
            val currentTime = appDateTimeFixture(minute=6)

            emailVerificationHandlerFixture.checkVerifiedAndCurrentlyValid(emailVerification, currentTime)
        }
    }
}
