package com.ttasjwi.board.system.global.domain

import com.ttasjwi.board.system.global.time.AppDateTime

abstract class DomainEvent<T>(
    val occurredAt: AppDateTime,
    val data: T,
)
