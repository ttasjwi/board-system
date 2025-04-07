package com.ttasjwi.board.system.application.member.usecase

import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture

class EmailVerificationStartUseCaseFixture : EmailVerificationStartUseCase {

    override fun startEmailVerification(request: EmailVerificationStartRequest): EmailVerificationStartResponse {
        return EmailVerificationStartResponse(
            email = request.email!!,
            codeExpiresAt = appDateTimeFixture().toZonedDateTime()
        )
    }
}
