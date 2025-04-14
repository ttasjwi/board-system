package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.member.domain.TokenRefreshRequest
import com.ttasjwi.board.system.member.domain.TokenRefreshResponse
import com.ttasjwi.board.system.member.domain.TokenRefreshUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenRefreshController(
    private val useCase: TokenRefreshUseCase,
) {

    @PostMapping("/api/v1/auth/token-refresh")
    fun tokenRefresh(@RequestBody request: TokenRefreshRequest): ResponseEntity<TokenRefreshResponse> {
        return ResponseEntity.ok(useCase.tokenRefresh(request))
    }
}
