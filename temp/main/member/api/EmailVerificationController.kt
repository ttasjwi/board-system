package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.member.application.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationResponse
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailVerificationController(
    private val useCase: EmailVerificationUseCase,
) {

    @PostMapping("/api/v1/members/email-verification")
    fun emailVerification(@RequestBody request: EmailVerificationRequest): ResponseEntity<EmailVerificationResponse> {
        return ResponseEntity.ok(useCase.emailVerification(request))
    }
}
