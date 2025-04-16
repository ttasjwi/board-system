package com.ttasjwi.board.system.user.domain.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.EmailVerificationRequest
import com.ttasjwi.board.system.user.domain.EmailVerificationResponse
import com.ttasjwi.board.system.user.domain.EmailVerificationUseCase

class EmailVerificationUseCaseFixture : EmailVerificationUseCase {

    override fun emailVerification(request: EmailVerificationRequest): EmailVerificationResponse {
        return EmailVerificationResponse(
            email = request.email!!,
            verificationExpiresAt = appDateTimeFixture(minute = 5).toZonedDateTime()
        )
    }
}
