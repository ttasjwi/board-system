package com.ttasjwi.board.system.core.time.impl

import com.ttasjwi.board.system.core.annotation.component.AppComponent
import com.ttasjwi.board.system.core.time.TimeManager
import java.time.ZonedDateTime

@AppComponent
class TimeManagerImpl : TimeManager {

    override fun now(): ZonedDateTime {
        TODO("Not yet implemented")
    }
}
