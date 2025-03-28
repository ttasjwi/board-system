package com.ttasjwi.board.system.auth.application.usecase

import java.time.ZonedDateTime

interface TokenRefreshUseCase {
    fun tokenRefresh(request: TokenRefreshRequest): TokenRefreshResult
}

data class TokenRefreshRequest(
    val refreshToken: String?,
)

data class TokenRefreshResult(
    val accessToken: String,
    val accessTokenExpiresAt: ZonedDateTime,
    val refreshToken: String,
    val refreshTokenExpiresAt: ZonedDateTime,
    val refreshTokenRefreshed: Boolean,
)
