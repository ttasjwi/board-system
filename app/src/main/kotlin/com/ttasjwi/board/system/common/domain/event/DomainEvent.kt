package com.ttasjwi.board.system.common.domain.event

import java.time.ZonedDateTime

abstract class DomainEvent<T>(
    val occurredAt: ZonedDateTime,
    val data: T,
)
