package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.member.domain.service.EmailVerificationCreator

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
