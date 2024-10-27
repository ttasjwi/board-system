package com.ttasjwi.board.system.email.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.email.domain.model.EmailVerification
import com.ttasjwi.board.system.email.domain.service.EmailVerificationHandler
import java.time.ZonedDateTime

@DomainService
internal class EmailVerificationHandlerImpl : EmailVerificationHandler {

    override fun verify(
        emailVerification: EmailVerification,
        code: String,
        currentTime: ZonedDateTime
    ): Result<EmailVerification> {
        TODO("Not yet implemented")
    }

    override fun checkCurrentlyValid(emailVerification: EmailVerification, currentTime: ZonedDateTime): Result<Unit> {
        TODO("Not yet implemented")
    }
}
