package com.ttasjwi.board.system.domain.member.service.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.member.model.EmailVerification
import com.ttasjwi.board.system.domain.member.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.domain.member.service.EmailVerificationCreator

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
