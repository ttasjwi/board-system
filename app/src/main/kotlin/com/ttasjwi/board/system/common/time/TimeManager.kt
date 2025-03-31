package com.ttasjwi.board.system.common.time

import java.time.ZonedDateTime

interface TimeManager {
    fun now(): ZonedDateTime
}
