package com.ttasjwi.board.system.domain.member.service.impl

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.domain.member.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.domain.member.event.EmailVerifiedEvent
import com.ttasjwi.board.system.domain.member.model.EmailVerification
import com.ttasjwi.board.system.domain.member.service.EmailVerificationEventCreator
import java.util.*

@DomainService
internal class EmailVerificationEventCreatorImpl : EmailVerificationEventCreator {

    override fun onVerificationStarted(
        emailVerification: EmailVerification,
        locale: Locale
    ): EmailVerificationStartedEvent {
        return EmailVerificationStartedEvent.create(emailVerification, locale)
    }

    override fun onVerified(emailVerification: EmailVerification): EmailVerifiedEvent {
        return EmailVerifiedEvent.create(emailVerification)
    }
}
