package com.ttasjwi.board.system.application.member.usecase

import java.time.ZonedDateTime

interface EmailVerificationStartUseCase {

    /**
     * 이메일 검증 절차를 시작합니다.
     */
    fun startEmailVerification(request: EmailVerificationStartRequest): EmailVerificationStartResponse
}

data class EmailVerificationStartRequest(
    val email: String?,
)

data class EmailVerificationStartResponse(
    val email: String,
    val codeExpiresAt: ZonedDateTime,
)
