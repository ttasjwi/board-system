package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.member.domain.service.EmailVerificationStartedEventPublisher
import org.springframework.stereotype.Component

@Component
class EmailVerificationStartedEventPublisherImpl : EmailVerificationStartedEventPublisher {

    override fun publishEvent(event: EmailVerificationStartedEvent) {
        TODO("not implemented")
    }
}
