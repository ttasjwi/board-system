package com.ttasjwi.board.system.common.time.impl

import com.ttasjwi.board.system.common.annotation.component.AppComponent
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.common.time.TimeRule
import java.time.ZonedDateTime

@AppComponent
class TimeManagerImpl : TimeManager {

    override fun now(): ZonedDateTime {
        return ZonedDateTime.now(TimeRule.ZONE_ID)
    }
}
