package com.ttasjwi.board.system.application.member.processor

import com.ttasjwi.board.system.application.member.dto.EmailVerificationStartCommand
import com.ttasjwi.board.system.domain.member.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.domain.member.service.EmailVerificationAppender
import com.ttasjwi.board.system.domain.member.service.EmailVerificationCreator
import com.ttasjwi.board.system.domain.member.service.EmailVerificationEventCreator
import com.ttasjwi.board.system.global.annotation.ApplicationProcessor
import com.ttasjwi.board.system.global.logging.getLogger

@ApplicationProcessor
internal class EmailVerificationStartProcessor(
    private val emailVerificationCreator: EmailVerificationCreator,
    private val emailVerificationAppender: EmailVerificationAppender,
    private val emailVerificationEventCreator: EmailVerificationEventCreator,
) {

    companion object {
        private val log = getLogger(EmailVerificationStartProcessor::class.java)
    }

    fun startEmailVerification(command: EmailVerificationStartCommand): EmailVerificationStartedEvent {
        log.info { "이메일 인증을 시작합니다" }

        val emailVerification = emailVerificationCreator.create(command.email, command.currenTime)

        emailVerificationAppender.append(
            emailVerification = emailVerification,
            expiresAt = emailVerification.codeExpiresAt
        )

        val event = emailVerificationEventCreator.onVerificationStarted(emailVerification, command.locale)

        log.info { "'이메일 인증됨' 이벤트 생성됨.(email=${event.data.email}" }

        return event
    }
}
