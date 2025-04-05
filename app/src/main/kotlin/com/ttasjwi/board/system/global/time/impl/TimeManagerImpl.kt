package com.ttasjwi.board.system.global.time.impl

import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.global.time.TimeManager

class TimeManagerImpl : TimeManager {

    override fun now(): AppDateTime {
        return AppDateTime.now()
    }
}
