package com.ttasjwi.board.system.application.auth.usecase

import java.time.ZonedDateTime

interface TokenRefreshUseCase {
    fun tokenRefresh(request: TokenRefreshRequest): TokenRefreshResponse
}

data class TokenRefreshRequest(
    val refreshToken: String?,
)

data class TokenRefreshResponse(
    val accessToken: String,
    val accessTokenType: String = "Bearer",
    val accessTokenExpiresAt: ZonedDateTime,
    val refreshToken: String,
    val refreshTokenExpiresAt: ZonedDateTime,
    val refreshTokenRefreshed: Boolean,
)
