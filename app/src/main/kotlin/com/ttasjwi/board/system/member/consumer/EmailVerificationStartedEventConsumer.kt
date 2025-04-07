package com.ttasjwi.board.system.member.consumer

import com.ttasjwi.board.system.application.member.usecase.EmailSendUseCase
import com.ttasjwi.board.system.domain.member.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.global.message.MessageResolver
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class EmailVerificationStartedEventConsumer(
    private val emailSendUseCase: EmailSendUseCase,
    private val messageResolver: MessageResolver,
) {

    @Async
    @EventListener(EmailVerificationStartedEvent::class)
    fun handleEmailVerificationStartedEvent(event: EmailVerificationStartedEvent) {
        emailSendUseCase.sendEmail(
            address = event.data.email,
            subject = messageResolver.resolve("EmailVerification.EmailSubject", event.data.locale),
            content = messageResolver.resolve(
                "EmailVerification.EmailContent",
                event.data.locale,
                listOf(event.data.code)
            ),
        )
    }
}
