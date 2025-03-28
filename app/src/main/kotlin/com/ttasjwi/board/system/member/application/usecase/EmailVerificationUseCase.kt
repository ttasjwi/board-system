package com.ttasjwi.board.system.member.application.usecase

import java.time.ZonedDateTime

interface EmailVerificationUseCase {

    /**
     * 회원 가입을 위한 이메일 인증을 처리합니다.
     */
    fun emailVerification(request: EmailVerificationRequest): EmailVerificationResult
}

data class EmailVerificationRequest(
    val email: String?,
    val code: String?,
)

data class EmailVerificationResult(
    val email: String,
    val verificationExpiresAt: ZonedDateTime,
)
