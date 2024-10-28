package com.ttasjwi.board.system.core.domain.event

import java.time.ZonedDateTime

abstract class DomainEvent<T>(
    val occurredAt: ZonedDateTime,
    val data: T,
)
