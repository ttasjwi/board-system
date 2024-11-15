package com.ttasjwi.board.system.core.time.impl

import com.ttasjwi.board.system.core.annotation.component.AppComponent
import com.ttasjwi.board.system.core.time.TimeManager
import com.ttasjwi.board.system.core.time.TimeRule
import java.time.ZonedDateTime

@AppComponent
class TimeManagerImpl : TimeManager {

    override fun now(): ZonedDateTime {
        return ZonedDateTime.now(TimeRule.ZONE_ID)
    }
}
