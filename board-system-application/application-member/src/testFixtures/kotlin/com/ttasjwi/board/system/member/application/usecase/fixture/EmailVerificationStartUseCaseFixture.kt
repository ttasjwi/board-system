package com.ttasjwi.board.system.member.application.usecase.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartRequest
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartResult
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartUseCase

class EmailVerificationStartUseCaseFixture : EmailVerificationStartUseCase {

    override fun startEmailVerification(request: EmailVerificationStartRequest): EmailVerificationStartResult {
        return EmailVerificationStartResult(
            email = request.email!!,
            codeExpiresAt = timeFixture()
        )
    }
}
