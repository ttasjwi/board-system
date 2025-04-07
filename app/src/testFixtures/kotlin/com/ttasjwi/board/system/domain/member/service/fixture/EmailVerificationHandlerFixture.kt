package com.ttasjwi.board.system.domain.member.service.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.member.model.EmailVerification
import com.ttasjwi.board.system.domain.member.model.fixture.emailVerificationFixtureVerified
import com.ttasjwi.board.system.domain.member.service.EmailVerificationHandler

class EmailVerificationHandlerFixture : EmailVerificationHandler {

    companion object {

        const val VERIFICATION_VALIDITY_MINUTE = 30L
    }

    override fun codeVerify(
        emailVerification: EmailVerification,
        code: String,
        currentTime: AppDateTime
    ): EmailVerification {
        return emailVerificationFixtureVerified(
            email = emailVerification.email,
            code = emailVerification.code,
            codeCreatedAt = emailVerification.codeCreatedAt,
            codeExpiresAt = emailVerification.codeExpiresAt,
            verifiedAt = currentTime,
            verificationExpiresAt = currentTime.plusMinutes(VERIFICATION_VALIDITY_MINUTE)
        )
    }

    override fun checkVerifiedAndCurrentlyValid(emailVerification: EmailVerification, currentTime: AppDateTime) {}
}
