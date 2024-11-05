package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.member.domain.event.EmailVerifiedEvent
import com.ttasjwi.board.system.member.domain.event.fixture.emailVerificationStartedEventFixture
import com.ttasjwi.board.system.member.domain.event.fixture.emailVerifiedEventFixture
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.service.EmailVerificationEventCreator
import java.util.Locale

class EmailVerificationEventCreatorFixture : EmailVerificationEventCreator {

    override fun onVerificationStarted(emailVerification: EmailVerification, locale: Locale): EmailVerificationStartedEvent {
        return emailVerificationStartedEventFixture(
            email = emailVerification.email.value,
            code = emailVerification.code,
            codeCreatedAt = emailVerification.codeCreatedAt,
            locale = locale,
        )
    }

    override fun onVerified(emailVerification: EmailVerification): EmailVerifiedEvent {
        return emailVerifiedEventFixture(
            email = emailVerification.email.value,
            verifiedAt = emailVerification.verifiedAt!!,
            verificationExpiresAt = emailVerification.verificationExpiresAt!!,
        )
    }
}
