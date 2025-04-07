package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.model.EmailVerification
import com.ttasjwi.board.system.domain.member.model.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.global.time.AppDateTime

class EmailVerificationCreatorFixture : EmailVerificationCreator {

    override fun create(email: String, currentTime: AppDateTime): EmailVerification {
        return emailVerificationFixtureNotVerified(
            email = email,
            code = "code",
            codeCreatedAt = currentTime,
            codeExpiresAt = currentTime.plusMinutes(5),
        )
    }
}
