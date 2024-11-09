package com.ttasjwi.board.system.auth.api

import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.core.api.SuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class LoginController {

    @PostMapping("/api/v1/auth/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<SuccessResponse<LoginResponse>> {
        TODO("not implemented")
    }
}

data class LoginResponse(
    val loginResult: LoginResult,
) {

    data class LoginResult(
        val accessToken: String,
        val accessTokenExpiresAt: ZonedDateTime,
        val tokenType: String = "Bearer",
        val refreshToken: String,
        val refreshTokenExpiresAt: ZonedDateTime,
    )
}
