package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.service.EmailVerificationHandler
import java.time.ZonedDateTime

@DomainService
internal class EmailVerificationHandlerImpl : EmailVerificationHandler {

    override fun codeVerify(emailVerification: EmailVerification, code: String, currentTime: ZonedDateTime): EmailVerification {
        return emailVerification.codeVerify(code, currentTime)
    }

    override fun checkVerifiedAndCurrentlyValid(emailVerification: EmailVerification, currentTime: ZonedDateTime) {
        emailVerification.checkVerifiedAndCurrentlyValid(currentTime)
    }
}
