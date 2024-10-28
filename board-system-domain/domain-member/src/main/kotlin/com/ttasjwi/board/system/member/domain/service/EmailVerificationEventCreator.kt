package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.member.domain.event.EmailVerifiedEvent
import com.ttasjwi.board.system.member.domain.model.EmailVerification

interface EmailVerificationEventCreator {

    fun onVerificationStarted(emailVerification: EmailVerification): EmailVerificationStartedEvent
    fun onVerified(emailVerification: EmailVerification): EmailVerifiedEvent
}
