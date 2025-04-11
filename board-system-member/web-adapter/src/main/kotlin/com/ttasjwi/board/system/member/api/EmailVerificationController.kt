package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import com.ttasjwi.board.system.member.domain.EmailVerificationRequest
import com.ttasjwi.board.system.member.domain.EmailVerificationResponse
import com.ttasjwi.board.system.member.domain.EmailVerificationUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailVerificationController(
    private val useCase: EmailVerificationUseCase,
) {

    @PermitAll
    @PostMapping("/api/v1/members/email-verification")
    fun emailVerification(@RequestBody request: EmailVerificationRequest): ResponseEntity<EmailVerificationResponse> {
        return ResponseEntity.ok(useCase.emailVerification(request))
    }
}
