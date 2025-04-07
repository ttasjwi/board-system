package com.ttasjwi.board.system.domain.auth.event.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.domain.auth.event.LoggedInEvent

fun loggedInEventFixture(
    accessToken: String = "accessToken",
    accessTokenExpiresAt: AppDateTime = appDateTimeFixture(minute = 30),
    refreshToken: String = "refreshToken",
    refreshTokenExpiresAt: AppDateTime = appDateTimeFixture(dayOfMonth = 2),
    loggedInAt: AppDateTime = appDateTimeFixture(minute = 0),
): LoggedInEvent {
    return LoggedInEvent(
        accessToken = accessToken,
        accessTokenExpiresAt = accessTokenExpiresAt,
        refreshToken = refreshToken,
        refreshTokenExpiresAt = refreshTokenExpiresAt,
        loggedInAt = loggedInAt,
    )
}
