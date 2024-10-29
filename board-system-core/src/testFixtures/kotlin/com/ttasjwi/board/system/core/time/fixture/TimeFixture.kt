package com.ttasjwi.board.system.core.time.fixture

import java.time.ZoneId
import java.time.ZonedDateTime

fun timeFixture(
    year: Int = 1980,
    month: Int = 1,
    dayOfMonth: Int = 1,
    hour: Int = 0,
    minute: Int = 0,
    second: Int = 0,
    nanoOfSecond: Int = 0,
    zone: ZoneId = ZoneId.of("Asia/Seoul")
): ZonedDateTime {
    return ZonedDateTime.of(
        year,
        month,
        dayOfMonth,
        hour,
        minute,
        second,
        nanoOfSecond,
        zone
    )
}
