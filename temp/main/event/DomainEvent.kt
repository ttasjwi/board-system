package com.ttasjwi.board.system.common.domain.event

import com.ttasjwi.board.system.common.time.AppDateTime

abstract class DomainEvent<T>(
    val occurredAt: AppDateTime,
    val data: T,
)
