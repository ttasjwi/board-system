package com.ttasjwi.board.system.auth.domain.event.fixture

import com.ttasjwi.board.system.auth.domain.event.LoggedInEvent
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import java.time.ZonedDateTime

fun loggedInEventFixture(
    accessToken: String = "accessToken",
    accessTokenExpiresAt: ZonedDateTime = timeFixture(minute = 30),
    refreshToken: String = "refreshToken",
    refreshTokenExpiresAt: ZonedDateTime = timeFixture(dayOfMonth = 2),
    loggedInAt: ZonedDateTime = timeFixture(minute = 0),
): LoggedInEvent {
    return LoggedInEvent(
        accessToken = accessToken,
        accessTokenExpiresAt = accessTokenExpiresAt,
        refreshToken = refreshToken,
        refreshTokenExpiresAt = refreshTokenExpiresAt,
        loggedInAt = loggedInAt,
    )
}
