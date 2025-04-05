package com.ttasjwi.board.system.global.time.fixture

import com.ttasjwi.board.system.global.time.AppDateTime

fun appDateTimeFixture(
    year: Int = 2025,
    month: Int = 1,
    dayOfMonth: Int = 1,
    hour: Int = 0,
    minute: Int = 0,
    second: Int = 0,
): AppDateTime {
    return AppDateTime.of(
        year = year,
        month = month,
        dayOfMonth = dayOfMonth,
        hour = hour,
        minute = minute,
        second = second,
    )
}
