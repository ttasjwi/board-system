package com.ttasjwi.board.system.email.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.email.domain.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.email.domain.event.EmailVerifiedEvent
import com.ttasjwi.board.system.email.domain.model.EmailVerification
import com.ttasjwi.board.system.email.domain.service.EmailVerificationEventCreator

@DomainService
internal class EmailVerificationEventCreatorImpl : EmailVerificationEventCreator {

    override fun onVerificationStarted(emailVerification: EmailVerification): EmailVerificationStartedEvent {
        TODO("Not yet implemented")
    }

    override fun onVerified(emailVerification: EmailVerification): EmailVerifiedEvent {
        TODO("Not yet implemented")
    }
}
