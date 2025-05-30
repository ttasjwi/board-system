package com.ttasjwi.board.system.user.api

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import com.ttasjwi.board.system.user.domain.EmailVerificationRequest
import com.ttasjwi.board.system.user.domain.EmailVerificationResponse
import com.ttasjwi.board.system.user.domain.EmailVerificationUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailVerificationController(
    private val useCase: EmailVerificationUseCase,
) {

    @PermitAll
    @PostMapping("/api/v1/users/email-verification")
    fun emailVerification(@RequestBody request: EmailVerificationRequest): ResponseEntity<EmailVerificationResponse> {
        return ResponseEntity.ok(useCase.emailVerification(request))
    }
}
