package com.ttasjwi.board.system.domain.member.service.fixture

import com.ttasjwi.board.system.domain.member.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.domain.member.event.EmailVerifiedEvent
import com.ttasjwi.board.system.domain.member.event.fixture.emailVerificationStartedEventFixture
import com.ttasjwi.board.system.domain.member.event.fixture.emailVerifiedEventFixture
import com.ttasjwi.board.system.domain.member.model.EmailVerification
import com.ttasjwi.board.system.domain.member.service.EmailVerificationEventCreator
import java.util.*

class EmailVerificationEventCreatorFixture : EmailVerificationEventCreator {

    override fun onVerificationStarted(
        emailVerification: EmailVerification,
        locale: Locale
    ): EmailVerificationStartedEvent {
        return emailVerificationStartedEventFixture(
            email = emailVerification.email,
            code = emailVerification.code,
            codeCreatedAt = emailVerification.codeCreatedAt,
            codeExpiresAt = emailVerification.codeExpiresAt,
            locale = locale,
        )
    }

    override fun onVerified(emailVerification: EmailVerification): EmailVerifiedEvent {
        return emailVerifiedEventFixture(
            email = emailVerification.email,
            verifiedAt = emailVerification.verifiedAt!!,
            verificationExpiresAt = emailVerification.verificationExpiresAt!!,
        )
    }
}
