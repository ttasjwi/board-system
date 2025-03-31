package com.ttasjwi.board.system.common.time.fixture

import com.ttasjwi.board.system.common.time.TimeRule
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
    zone: ZoneId = TimeRule.ZONE_ID
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
