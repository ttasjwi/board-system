package com.ttasjwi.board.system.auth.domain.event.fixture

import com.ttasjwi.board.system.auth.domain.event.LoggedInEvent
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

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
