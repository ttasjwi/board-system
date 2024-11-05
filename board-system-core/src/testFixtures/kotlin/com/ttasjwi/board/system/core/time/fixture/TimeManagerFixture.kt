package com.ttasjwi.board.system.core.time.fixture

import com.ttasjwi.board.system.core.time.TimeManager
import java.time.ZonedDateTime

class TimeManagerFixture(
    private var currentTime: ZonedDateTime = timeFixture()
): TimeManager {

    override fun now(): ZonedDateTime {
        return currentTime
    }

    fun changeCurrentTime(currentTime: ZonedDateTime) {
        this.currentTime = currentTime
    }
}
