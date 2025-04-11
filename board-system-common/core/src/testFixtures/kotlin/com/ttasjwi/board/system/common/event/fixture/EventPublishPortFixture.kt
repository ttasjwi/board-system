package com.ttasjwi.board.system.common.event.fixture

import com.ttasjwi.board.system.common.event.Event
import com.ttasjwi.board.system.common.event.EventPayload
import com.ttasjwi.board.system.common.event.EventPublishPort

class EventPublishPortFixture : EventPublishPort {

    override fun publish(event: Event<out EventPayload>) {}
}
