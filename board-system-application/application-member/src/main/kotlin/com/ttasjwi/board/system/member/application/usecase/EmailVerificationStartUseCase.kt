package com.ttasjwi.board.system.member.application.usecase

import java.time.ZonedDateTime

interface EmailVerificationStartUseCase {

    /**
     * 이메일 검증 절차를 시작합니다.
     */
    fun startEmailVerification(request: EmailVerificationStartRequest): EmailVerificationStartResult
}

data class EmailVerificationStartRequest(
    val email: String?,
)

data class EmailVerificationStartResult(
    val email: String,
    val codeExpiresAt: ZonedDateTime,
)
