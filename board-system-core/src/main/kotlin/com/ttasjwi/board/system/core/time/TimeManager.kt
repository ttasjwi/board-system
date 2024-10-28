package com.ttasjwi.board.system.core.time

import java.time.ZonedDateTime

interface TimeManager {
    fun now(): ZonedDateTime
}
