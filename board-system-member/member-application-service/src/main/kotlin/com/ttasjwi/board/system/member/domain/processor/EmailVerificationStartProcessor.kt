package com.ttasjwi.board.system.member.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.common.event.EventPublishPort
import com.ttasjwi.board.system.member.domain.dto.EmailVerificationStartCommand
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.port.EmailVerificationPersistencePort

@ApplicationProcessor
internal class EmailVerificationStartProcessor(
    private val emailVerificationPersistencePort: EmailVerificationPersistencePort,
    private val eventPublishPort: EventPublishPort,
) {

    fun startEmailVerification(command: EmailVerificationStartCommand): EmailVerification {
        val emailVerification = EmailVerification.create(command.email, command.currenTime)

        emailVerificationPersistencePort.save(
            emailVerification = emailVerification,
            expiresAt = emailVerification.codeExpiresAt
        )

        eventPublishPort.publish(
            EmailVerificationStartedEvent.create(
                email = emailVerification.email,
                code = emailVerification.code,
                codeCreatedAt = emailVerification.codeCreatedAt,
                codeExpiresAt = emailVerification.codeExpiresAt,
                locale = command.locale,
                eventCreatedAt = command.currenTime
            )
        )
        return emailVerification
    }
}
