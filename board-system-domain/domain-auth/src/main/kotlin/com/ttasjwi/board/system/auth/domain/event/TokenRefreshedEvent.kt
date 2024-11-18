package com.ttasjwi.board.system.auth.domain.event

import com.ttasjwi.board.system.core.domain.event.DomainEvent
import java.time.ZonedDateTime

class TokenRefreshedEvent
internal constructor(
    accessToken: String,
    accessTokenExpiresAt: ZonedDateTime,
    refreshToken: String,
    refreshTokenExpiresAt: ZonedDateTime,
    refreshTokenRefreshed: Boolean,
    refreshedAt: ZonedDateTime,
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
        val accessTokenExpiresAt: ZonedDateTime,
        val refreshToken: String,
        val refreshTokenExpiresAt: ZonedDateTime,
        val refreshTokenRefreshed: Boolean,
    )

}
