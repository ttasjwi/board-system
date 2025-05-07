package com.ttasjwi.board.system.user.api

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import com.ttasjwi.board.system.user.domain.SocialLoginRequest
import com.ttasjwi.board.system.user.domain.SocialLoginResponse
import com.ttasjwi.board.system.user.domain.SocialLoginUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SocialLoginController(
    private val socialLoginUseCase: SocialLoginUseCase
) {

    @PermitAll
    @GetMapping("/api/v1/auth/social-login")
    fun socialLoginGet(@ModelAttribute request: SocialLoginRequest): ResponseEntity<SocialLoginResponse> {
        val response = socialLoginUseCase.socialLogin(request)
        return ResponseEntity.ok(response)
    }

    @PermitAll
    @PostMapping("/api/v1/auth/social-login")
    fun socialLoginPost(@RequestBody request: SocialLoginRequest): ResponseEntity<SocialLoginResponse> {
        val response = socialLoginUseCase.socialLogin(request)
        return ResponseEntity.ok(response)
    }
}
