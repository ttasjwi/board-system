package com.ttasjwi.board.system.common.time.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.TimeManager

class TimeManagerFixture(
    private var currentTime: AppDateTime = appDateTimeFixture()
) : TimeManager {

    override fun now(): AppDateTime {
        return currentTime
    }

    fun changeCurrentTime(currentTime: AppDateTime) {
        this.currentTime = currentTime
    }
}
