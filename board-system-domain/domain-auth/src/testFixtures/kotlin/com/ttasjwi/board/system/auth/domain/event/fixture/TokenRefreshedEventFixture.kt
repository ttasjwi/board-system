package com.ttasjwi.board.system.auth.domain.event.fixture

import com.ttasjwi.board.system.auth.domain.event.TokenRefreshedEvent
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import java.time.ZonedDateTime

fun tokenRefreshedEventFixture(
    accessToken: String = "accessToken",
    accessTokenExpiresAt: ZonedDateTime = timeFixture(minute = 30),
    refreshToken: String = "refreshToken",
    refreshTokenExpiresAt: ZonedDateTime = timeFixture(dayOfMonth = 2),
    refreshedAt: ZonedDateTime = timeFixture(minute = 0),
    refreshTokenRefreshed: Boolean = false,
): TokenRefreshedEvent {
    return TokenRefreshedEvent(
        accessToken = accessToken,
        accessTokenExpiresAt = accessTokenExpiresAt,
        refreshToken = refreshToken,
        refreshTokenExpiresAt = refreshTokenExpiresAt,
        refreshedAt = refreshedAt,
        refreshTokenRefreshed = refreshTokenRefreshed
    )
}
