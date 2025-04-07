package com.ttasjwi.board.system.common.time

internal class DefaultTimeManager : TimeManager {
    override fun now(): AppDateTime {
        return AppDateTime.now()
    }
}
