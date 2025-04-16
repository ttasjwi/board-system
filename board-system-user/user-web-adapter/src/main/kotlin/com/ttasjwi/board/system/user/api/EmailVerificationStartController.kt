package com.ttasjwi.board.system.user.api

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import com.ttasjwi.board.system.user.domain.EmailVerificationStartRequest
import com.ttasjwi.board.system.user.domain.EmailVerificationStartResponse
import com.ttasjwi.board.system.user.domain.EmailVerificationStartUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailVerificationStartController(
    private val useCase: EmailVerificationStartUseCase,
) {

    @PermitAll
    @PostMapping("/api/v1/users/email-verification/start")
    fun startEmailVerification(@RequestBody request: EmailVerificationStartRequest): ResponseEntity<EmailVerificationStartResponse> {
        return ResponseEntity.ok(useCase.startEmailVerification(request))
    }
}
