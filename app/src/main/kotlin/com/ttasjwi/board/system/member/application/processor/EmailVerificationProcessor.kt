package com.ttasjwi.board.system.member.application.processor

import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.member.application.dto.EmailVerificationCommand
import com.ttasjwi.board.system.member.application.exception.EmailVerificationNotFoundException
import com.ttasjwi.board.system.member.domain.event.EmailVerifiedEvent
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.service.EmailVerificationAppender
import com.ttasjwi.board.system.member.domain.service.EmailVerificationEventCreator
import com.ttasjwi.board.system.member.domain.service.EmailVerificationFinder
import com.ttasjwi.board.system.member.domain.service.EmailVerificationHandler
import org.springframework.stereotype.Component

@Component
internal class EmailVerificationProcessor(
    private val emailVerificationFinder: EmailVerificationFinder,
    private val emailVerificationHandler: EmailVerificationHandler,
    private val emailVerificationAppender: EmailVerificationAppender,
    private val emailVerificationEventCreator: EmailVerificationEventCreator,
) {

    companion object {
        private val log = getLogger(EmailVerificationProcessor::class.java)
    }


    fun verify(command: EmailVerificationCommand): EmailVerifiedEvent {
        // 이메일에 대응하는 이메일 인증 조회
        val emailVerification = getEmailVerification(command)

        // 이메일 인증 처리
        val verifiedEmailVerification =
            emailVerificationHandler.codeVerify(emailVerification, command.code, command.currentTime)

        // 이메일 인증 저장
        emailVerificationAppender.append(verifiedEmailVerification, verifiedEmailVerification.verificationExpiresAt!!)

        // 이메일 인증됨 이벤트 생성, 반환
        return emailVerificationEventCreator.onVerified(verifiedEmailVerification)
    }

    private fun getEmailVerification(command: EmailVerificationCommand): EmailVerification {
        log.info { "이메일(email=${command.email})에 대응하는 이메일 인증을 조회합니다." }
        val emailVerification = emailVerificationFinder.findByEmailOrNull(command.email)

        if (emailVerification == null) {
            log.warn { "이메일(email=${command.email})에 대응하는 이메일 인증이 없습니다. 없거나, 만료됐습니다." }
            throw EmailVerificationNotFoundException(command.email)
        }
        log.info { "이메일(email=${command.email})에 대응하는 이메일 인증을 찾았습니다. (codeCreatedAt=${emailVerification.codeCreatedAt},codeExpiresAt=${emailVerification.codeExpiresAt})" }
        return emailVerification
    }
}
