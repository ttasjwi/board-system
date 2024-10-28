package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.annotation.component.ApplicationService
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartRequest
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartResult
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartUseCase

@ApplicationService
internal class EmailVerificationStartApplicationService : EmailVerificationStartUseCase{

    override fun startEmailVerification(request: EmailVerificationStartRequest): EmailVerificationStartResult {
        TODO("Not yet implemented")
    }
}
