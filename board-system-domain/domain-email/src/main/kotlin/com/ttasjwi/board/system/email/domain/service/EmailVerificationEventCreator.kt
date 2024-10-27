package com.ttasjwi.board.system.email.domain.service

import com.ttasjwi.board.system.email.domain.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.email.domain.event.EmailVerifiedEvent
import com.ttasjwi.board.system.email.domain.model.EmailVerification

interface EmailVerificationEventCreator {

    fun onVerificationStarted(emailVerification: EmailVerification): EmailVerificationStartedEvent
    fun onVerified(emailVerification: EmailVerification): EmailVerifiedEvent
}
