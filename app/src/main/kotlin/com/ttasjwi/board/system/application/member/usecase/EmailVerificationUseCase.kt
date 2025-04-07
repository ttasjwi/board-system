package com.ttasjwi.board.system.application.member.usecase

import java.time.ZonedDateTime

interface EmailVerificationUseCase {

    /**
     * 회원 가입을 위한 이메일 인증을 처리합니다.
     */
    fun emailVerification(request: EmailVerificationRequest): EmailVerificationResponse
}

data class EmailVerificationRequest(
    val email: String?,
    val code: String?,
)

data class EmailVerificationResponse(
    val email: String,
    val verificationExpiresAt: ZonedDateTime,
)
