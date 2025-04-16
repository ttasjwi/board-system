package com.ttasjwi.board.system.user.domain

import java.time.ZonedDateTime

interface TokenRefreshUseCase {
    fun tokenRefresh(request: TokenRefreshRequest): TokenRefreshResponse
}

data class TokenRefreshRequest(
    val refreshToken: String?,
)

data class TokenRefreshResponse(
    val accessToken: String,
    val accessTokenType: String,
    val accessTokenExpiresAt: ZonedDateTime,
    val refreshToken: String,
    val refreshTokenExpiresAt: ZonedDateTime,
    val refreshTokenRefreshed: Boolean,
)
