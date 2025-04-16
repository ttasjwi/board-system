package com.ttasjwi.board.system.user.domain.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.EmailVerificationStartRequest
import com.ttasjwi.board.system.user.domain.EmailVerificationStartResponse
import com.ttasjwi.board.system.user.domain.EmailVerificationStartUseCase

class EmailVerificationStartUseCaseFixture : EmailVerificationStartUseCase {

    override fun startEmailVerification(request: EmailVerificationStartRequest): EmailVerificationStartResponse {
        return EmailVerificationStartResponse(
            email = request.email!!,
            codeExpiresAt = appDateTimeFixture().toZonedDateTime()
        )
    }
}
