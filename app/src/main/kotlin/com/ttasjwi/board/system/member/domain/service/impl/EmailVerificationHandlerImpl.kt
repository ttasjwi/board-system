package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.service.EmailVerificationHandler

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
