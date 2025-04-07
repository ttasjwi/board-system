package com.ttasjwi.board.system.application.auth.usecase

import java.time.ZonedDateTime

interface LoginUseCase {
    fun login(request: LoginRequest): LoginResponse
}

data class LoginRequest(
    val email: String?,
    val password: String?,
)

data class LoginResponse(
    val accessToken: String,
    val accessTokenExpiresAt: ZonedDateTime,
    val accessTokenType: String = "Bearer",
    val refreshToken: String,
    val refreshTokenExpiresAt: ZonedDateTime,
)
