package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartRequest
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartResponse
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailVerificationStartController(
    private val useCase: EmailVerificationStartUseCase,
) {

    @PostMapping("/api/v1/members/email-verification/start")
    fun startEmailVerification(@RequestBody request: EmailVerificationStartRequest): ResponseEntity<EmailVerificationStartResponse> {
        return ResponseEntity.ok(useCase.startEmailVerification(request))
    }
}
