package com.ttasjwi.board.system.common.time.impl

import com.ttasjwi.board.system.common.annotation.component.AppComponent
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.TimeManager

@AppComponent
class TimeManagerImpl : TimeManager {

    override fun now(): AppDateTime {
        return AppDateTime.now()
    }
}
