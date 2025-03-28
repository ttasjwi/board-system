package com.ttasjwi.board.system.common.domain.event

interface DomainEventPublisher<T: DomainEvent<*>> {
    fun publishEvent(event: T)
}
