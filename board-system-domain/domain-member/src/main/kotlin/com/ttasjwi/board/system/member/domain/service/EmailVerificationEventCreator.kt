package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.member.domain.event.EmailVerifiedEvent
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import java.util.*

interface EmailVerificationEventCreator {

    fun onVerificationStarted(emailVerification: EmailVerification, locale: Locale): EmailVerificationStartedEvent
    fun onVerified(emailVerification: EmailVerification): EmailVerifiedEvent
}
