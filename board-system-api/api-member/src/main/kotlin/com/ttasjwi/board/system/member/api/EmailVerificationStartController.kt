package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class EmailVerificationStartController {

    @PostMapping("/api/v1/members/email-verification/start")
    fun startEmailVerification(): ResponseEntity<SuccessResponse<EmailVerificationStartResponse>> {
        TODO("Not yet implemented")
    }
}

data class EmailVerificationStartResponse(
    val verificationStartedResult: VerificationStartedResult
) {

    data class VerificationStartedResult(
        val email: String,
        val codeExpiresAt: ZonedDateTime
    )
}
