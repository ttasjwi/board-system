package com.ttasjwi.board.system.global.domain

interface DomainEventPublisher<T: DomainEvent<*>> {
    fun publishEvent(event: T)
}
