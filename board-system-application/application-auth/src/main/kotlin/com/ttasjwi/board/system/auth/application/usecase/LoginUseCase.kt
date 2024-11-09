package com.ttasjwi.board.system.auth.application.usecase

import java.time.ZonedDateTime

interface LoginUseCase {

    fun login(request: LoginRequest): LoginResult
}

data class LoginRequest(
    val email: String?,
    val password: String?,
)

data class LoginResult(
    val accessToken: String,
    val accessTokenExpiresAt: ZonedDateTime,
    val refreshToken: String,
    val refreshTokenExpiresAt: ZonedDateTime,
)
