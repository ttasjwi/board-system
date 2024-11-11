package com.ttasjwi.board.system.auth.domain.event

import com.ttasjwi.board.system.core.domain.event.DomainEvent
import java.time.ZonedDateTime

class LoggedInEvent
internal constructor(
    accessToken: String,
    accessTokenExpiresAt: ZonedDateTime,
    refreshToken: String,
    refreshTokenExpiresAt: ZonedDateTime,
    loggedInAt: ZonedDateTime,
) : DomainEvent<LoggedInEvent.Data>(
    occurredAt = loggedInAt, data = Data(
        accessToken = accessToken,
        accessTokenExpiresAt = accessTokenExpiresAt,
        refreshToken = refreshToken,
        refreshTokenExpiresAt = refreshTokenExpiresAt,
    )
) {

    class Data(
        val accessToken: String,
        val accessTokenExpiresAt: ZonedDateTime,
        val refreshToken: String,
        val refreshTokenExpiresAt: ZonedDateTime,
    )
}
