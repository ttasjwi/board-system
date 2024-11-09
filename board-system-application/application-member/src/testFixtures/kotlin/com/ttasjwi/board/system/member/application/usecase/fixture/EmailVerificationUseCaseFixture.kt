package com.ttasjwi.board.system.member.application.usecase.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationResult
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationUseCase

class EmailVerificationUseCaseFixture : EmailVerificationUseCase {

    override fun emailVerification(request: EmailVerificationRequest): EmailVerificationResult {
        return EmailVerificationResult(
            email = request.email!!,
            verificationExpiresAt = timeFixture(minute = 5)
        )
    }
}
