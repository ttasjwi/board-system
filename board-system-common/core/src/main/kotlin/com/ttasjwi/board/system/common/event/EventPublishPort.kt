package com.ttasjwi.board.system.common.event

interface EventPublishPort {
    fun publish(event: Event<out EventPayload>)
}
