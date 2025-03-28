package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.member.domain.event.EmailVerifiedEvent
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.service.EmailVerificationEventCreator
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
