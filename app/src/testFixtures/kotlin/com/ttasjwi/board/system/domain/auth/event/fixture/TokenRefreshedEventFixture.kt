package com.ttasjwi.board.system.domain.auth.event.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.domain.auth.event.TokenRefreshedEvent

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
