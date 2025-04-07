package com.ttasjwi.board.system.api.controller.auth

import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshRequest
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshResponse
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenRefreshController(
    private val useCase: TokenRefreshUseCase,
) {

    @PostMapping("/api/v1/auth/token-refresh")
    fun login(@RequestBody request: TokenRefreshRequest): ResponseEntity<TokenRefreshResponse> {
        return ResponseEntity.ok(useCase.tokenRefresh(request))
    }
}
