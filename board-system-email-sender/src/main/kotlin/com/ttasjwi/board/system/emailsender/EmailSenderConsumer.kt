package com.ttasjwi.board.system.emailsender

import com.ttasjwi.board.system.common.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.common.message.MessageResolver
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class EmailSenderConsumer(
    private val messageResolver: MessageResolver,
    private val emailSender: EmailSender,
) {

    @Async
    @EventListener(EmailVerificationStartedEvent::class)
    fun handleEmailVerificationStartedEvent(event: EmailVerificationStartedEvent) {
        emailSender.send(
            address = event.payload.email,
            subject = messageResolver.resolve("EmailVerification.EmailSubject", event.payload.locale),
            content = messageResolver.resolve("EmailVerification.EmailContent", event.payload.locale, listOf(event.payload.code))
        )
    }
}
