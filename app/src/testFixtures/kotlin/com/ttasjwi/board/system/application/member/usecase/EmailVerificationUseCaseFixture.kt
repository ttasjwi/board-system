package com.ttasjwi.board.system.application.member.usecase

import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture

class EmailVerificationUseCaseFixture : EmailVerificationUseCase {

    override fun emailVerification(request: EmailVerificationRequest): EmailVerificationResponse {
        return EmailVerificationResponse(
            email = request.email!!,
            verificationExpiresAt = appDateTimeFixture(minute = 5).toZonedDateTime()
        )
    }
}
