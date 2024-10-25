package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class EmailVerificationController {

    @PostMapping("/api/v1/members/email-verification")
    fun emailVerification(): ResponseEntity<SuccessResponse<EmailVerificationResponse>> {
        TODO("Not yet implemented")
    }

}

data class EmailVerificationResponse(
    val verificationResult: VerificationResult
) {

    data class VerificationResult(
        val email: String,
        val verificationExpiresAt: ZonedDateTime,
    )
}
