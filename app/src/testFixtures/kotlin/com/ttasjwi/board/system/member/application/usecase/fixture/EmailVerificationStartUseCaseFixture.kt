package com.ttasjwi.board.system.member.application.usecase.fixture

import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartRequest
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartResponse
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartUseCase

class EmailVerificationStartUseCaseFixture : EmailVerificationStartUseCase {

    override fun startEmailVerification(request: EmailVerificationStartRequest): EmailVerificationStartResponse {
        return EmailVerificationStartResponse(
            email = request.email!!,
            codeExpiresAt = appDateTimeFixture().toZonedDateTime()
        )
    }
}
