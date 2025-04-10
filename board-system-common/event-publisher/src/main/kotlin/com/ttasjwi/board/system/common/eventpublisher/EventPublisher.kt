package com.ttasjwi.board.system.common.eventpublisher

import com.ttasjwi.board.system.common.event.Event
import com.ttasjwi.board.system.common.event.EventPayload
import com.ttasjwi.board.system.common.event.EventPublishPort
import org.springframework.context.ApplicationEventPublisher

class EventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher,
) : EventPublishPort {

    override fun publish(event: Event<out EventPayload>) {
        applicationEventPublisher.publishEvent(event)
    }
}
