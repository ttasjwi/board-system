package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.member.domain.service.EmailVerificationCreator
import java.time.ZonedDateTime

class EmailVerificationCreatorFixture : EmailVerificationCreator {

    override fun create(email: Email, currentTime: ZonedDateTime): EmailVerification {
        return emailVerificationFixtureNotVerified(
            email = email.value,
            code = "code",
            codeCreatedAt = currentTime,
            codeExpiresAt = currentTime.plusMinutes(5),
        )
    }
}
