package com.ttasjwi.board.system.member.domain.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.EmailVerificationRequest
import com.ttasjwi.board.system.member.domain.EmailVerificationResponse
import com.ttasjwi.board.system.member.domain.EmailVerificationUseCase

class EmailVerificationUseCaseFixture : EmailVerificationUseCase {

    override fun emailVerification(request: EmailVerificationRequest): EmailVerificationResponse {
        return EmailVerificationResponse(
            email = request.email!!,
            verificationExpiresAt = appDateTimeFixture(minute = 5).toZonedDateTime()
        )
    }
}
