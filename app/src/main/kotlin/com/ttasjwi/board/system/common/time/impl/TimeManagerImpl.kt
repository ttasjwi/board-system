package com.ttasjwi.board.system.common.time.impl

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.TimeManager

@DomainService
class TimeManagerImpl : TimeManager {

    override fun now(): AppDateTime {
        return AppDateTime.now()
    }
}
