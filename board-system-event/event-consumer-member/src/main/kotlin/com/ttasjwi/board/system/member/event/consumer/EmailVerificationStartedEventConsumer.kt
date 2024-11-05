package com.ttasjwi.board.system.member.event.consumer

import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class EmailVerificationStartedEventConsumer {

    @EventListener(EmailVerificationStartedEvent::class)
    fun handleEmailVerificationStartedEvent(event: EmailVerificationStartedEvent) {
        TODO("not implemented")
    }
}
