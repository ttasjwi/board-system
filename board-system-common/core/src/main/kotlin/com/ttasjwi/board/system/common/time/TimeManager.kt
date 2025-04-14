package com.ttasjwi.board.system.common.time

interface TimeManager {

    companion object {
        fun default(): TimeManager {
            return DefaultTimeManager()
        }
    }

    fun now(): AppDateTime
}
