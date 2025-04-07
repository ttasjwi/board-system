package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.domain.member.event.EmailVerifiedEvent
import com.ttasjwi.board.system.domain.member.model.EmailVerification
import java.util.*

interface EmailVerificationEventCreator {

    fun onVerificationStarted(emailVerification: EmailVerification, locale: Locale): EmailVerificationStartedEvent
    fun onVerified(emailVerification: EmailVerification): EmailVerifiedEvent
}
