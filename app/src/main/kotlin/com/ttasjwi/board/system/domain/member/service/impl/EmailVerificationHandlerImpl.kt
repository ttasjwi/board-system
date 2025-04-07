package com.ttasjwi.board.system.domain.member.service.impl

import com.ttasjwi.board.system.domain.member.model.EmailVerification
import com.ttasjwi.board.system.domain.member.service.EmailVerificationHandler
import com.ttasjwi.board.system.global.annotation.DomainService
import com.ttasjwi.board.system.global.time.AppDateTime

@DomainService
internal class EmailVerificationHandlerImpl : EmailVerificationHandler {

    override fun codeVerify(
        emailVerification: EmailVerification,
        code: String,
        currentTime: AppDateTime
    ): EmailVerification {
        return emailVerification.codeVerify(code, currentTime)
    }

    override fun checkVerifiedAndCurrentlyValid(emailVerification: EmailVerification, currentTime: AppDateTime) {
        emailVerification.checkVerifiedAndCurrentlyValid(currentTime)
    }
}
