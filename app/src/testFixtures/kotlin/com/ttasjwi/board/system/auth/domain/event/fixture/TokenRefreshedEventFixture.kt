package com.ttasjwi.board.system.auth.domain.event.fixture

import com.ttasjwi.board.system.auth.domain.event.TokenRefreshedEvent
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

fun tokenRefreshedEventFixture(
    accessToken: String = "accessToken",
    accessTokenExpiresAt: AppDateTime = appDateTimeFixture(minute = 30),
    refreshToken: String = "refreshToken",
    refreshTokenExpiresAt: AppDateTime = appDateTimeFixture(dayOfMonth = 2),
    refreshedAt: AppDateTime = appDateTimeFixture(minute = 0),
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
