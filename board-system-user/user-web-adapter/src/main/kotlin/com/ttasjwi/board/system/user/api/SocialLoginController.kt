package com.ttasjwi.board.system.user.api

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import com.ttasjwi.board.system.user.domain.SocialLoginRequest
import com.ttasjwi.board.system.user.domain.SocialLoginResponse
import com.ttasjwi.board.system.user.domain.SocialLoginUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SocialLoginController(
    private val socialLoginUseCase: SocialLoginUseCase
) {

    @PermitAll
    @PostMapping("/api/v1/auth/social-login")
    fun socialLoginPost(@RequestBody request: SocialLoginRequest): ResponseEntity<SocialLoginResponse> {
        val response = socialLoginUseCase.socialLogin(request)
        return ResponseEntity.ok(response)
    }
}
