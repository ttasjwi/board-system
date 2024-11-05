package com.ttasjwi.board.system.core.domain.event

interface DomainEventPublisher<T: DomainEvent<*>> {
    fun publishEvent(event: T)
}
