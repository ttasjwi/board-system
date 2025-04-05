package com.ttasjwi.board.system.auth.domain.event

import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.global.domain.DomainEvent
import com.ttasjwi.board.system.global.time.AppDateTime

class LoggedInEvent
internal constructor(
    accessToken: String,
    accessTokenExpiresAt: AppDateTime,
    refreshToken: String,
    refreshTokenExpiresAt: AppDateTime,
    loggedInAt: AppDateTime,
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
        val accessTokenExpiresAt: AppDateTime,
        val refreshToken: String,
        val refreshTokenExpiresAt: AppDateTime,
    )

    companion object {

        internal fun create(accessToken: AccessToken, refreshToken: RefreshToken): LoggedInEvent {
            return LoggedInEvent(
                accessToken = accessToken.tokenValue,
                accessTokenExpiresAt = accessToken.expiresAt,
                refreshToken = refreshToken.tokenValue,
                refreshTokenExpiresAt = refreshToken.expiresAt,
                loggedInAt = accessToken.issuedAt
            )
        }
    }
}
