package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.annotation.component.ApplicationService
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationResult
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationUseCase

@ApplicationService
internal class EmailVerificationApplicationService : EmailVerificationUseCase{

    override fun emailVerification(request: EmailVerificationRequest): EmailVerificationResult {
        TODO("Not yet implemented")
    }
}
