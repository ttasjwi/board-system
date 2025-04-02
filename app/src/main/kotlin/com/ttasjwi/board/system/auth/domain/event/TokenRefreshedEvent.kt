package com.ttasjwi.board.system.auth.domain.event

import com.ttasjwi.board.system.common.domain.event.DomainEvent
import com.ttasjwi.board.system.common.time.AppDateTime

class TokenRefreshedEvent
internal constructor(
    accessToken: String,
    accessTokenExpiresAt: AppDateTime,
    refreshToken: String,
    refreshTokenExpiresAt: AppDateTime,
    refreshTokenRefreshed: Boolean,
    refreshedAt: AppDateTime,
) : DomainEvent<TokenRefreshedEvent.Data>(
    occurredAt = refreshedAt, data = Data(
        accessToken = accessToken,
        accessTokenExpiresAt = accessTokenExpiresAt,
        refreshToken = refreshToken,
        refreshTokenExpiresAt = refreshTokenExpiresAt,
        refreshTokenRefreshed = refreshTokenRefreshed
    )
) {

    class Data(
        val accessToken: String,
        val accessTokenExpiresAt: AppDateTime,
        val refreshToken: String,
        val refreshTokenExpiresAt: AppDateTime,
        val refreshTokenRefreshed: Boolean,
    )
}
