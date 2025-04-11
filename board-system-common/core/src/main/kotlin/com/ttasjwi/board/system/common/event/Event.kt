package com.ttasjwi.board.system.common.event

import java.time.ZonedDateTime

abstract class Event<T : EventPayload>(
    val eventType: EventType,
    val createdAt: ZonedDateTime,
    val payload: T,
)
