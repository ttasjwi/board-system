package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureVerified
import com.ttasjwi.board.system.member.domain.service.EmailVerificationHandler
import java.time.ZonedDateTime

class EmailVerificationHandlerFixture : EmailVerificationHandler {

    companion object {

        const val VERIFICATION_VALIDITY_MINUTE = 30L
    }

    override fun codeVerify(emailVerification: EmailVerification, code: String, currentTime: ZonedDateTime): EmailVerification {
        return emailVerificationFixtureVerified(
            email = emailVerification.email.value,
            code = emailVerification.code,
            codeCreatedAt = emailVerification.codeCreatedAt,
            codeExpiresAt = emailVerification.codeExpiresAt,
            verifiedAt = currentTime,
            verificationExpiresAt = currentTime.plusMinutes(VERIFICATION_VALIDITY_MINUTE)
        )
    }

    override fun checkVerifiedAndCurrentlyValid(emailVerification: EmailVerification, currentTime: ZonedDateTime) {
        TODO("Not yet implemented")
    }
}
