package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.member.domain.event.EmailVerifiedEvent
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.service.EmailVerificationEventCreator

@DomainService
internal class EmailVerificationEventCreatorImpl : EmailVerificationEventCreator {

    override fun onVerificationStarted(emailVerification: EmailVerification): EmailVerificationStartedEvent {
        TODO("Not yet implemented")
    }

    override fun onVerified(emailVerification: EmailVerification): EmailVerifiedEvent {
        TODO("Not yet implemented")
    }
}
