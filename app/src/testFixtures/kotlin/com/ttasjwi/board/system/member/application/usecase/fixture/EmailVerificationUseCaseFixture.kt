package com.ttasjwi.board.system.member.application.usecase.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationResponse
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationUseCase

class EmailVerificationUseCaseFixture : EmailVerificationUseCase {

    override fun emailVerification(request: EmailVerificationRequest): EmailVerificationResponse {
        return EmailVerificationResponse(
            email = request.email!!,
            verificationExpiresAt = appDateTimeFixture(minute = 5).toZonedDateTime()
        )
    }
}
