package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.annotation.component.ApplicationService
import com.ttasjwi.board.system.core.application.TransactionRunner
import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.application.mapper.EmailVerificationStartCommandMapper
import com.ttasjwi.board.system.member.application.processor.EmailVerificationStartProcessor
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartRequest
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartResult
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartUseCase
import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.member.domain.service.EmailVerificationStartedEventPublisher

@ApplicationService
internal class EmailVerificationStartApplicationService(
    private val commandMapper: EmailVerificationStartCommandMapper,
    private val processor: EmailVerificationStartProcessor,
    private val transactionRunner: TransactionRunner,
    private val eventPublisher: EmailVerificationStartedEventPublisher
) : EmailVerificationStartUseCase {

    companion object {
        private val log = getLogger(EmailVerificationStartApplicationService::class.java)
    }

    override fun startEmailVerification(request: EmailVerificationStartRequest): EmailVerificationStartResult {
        log.info{ "이메일 인증 시작 요청을 받았습니다."}

        // 유효성 검사를 거쳐서 명령으로 변환
        val command = commandMapper.mapToCommand(request)

        // 프로세서에 위임
        val event = transactionRunner.run {
            processor.startEmailVerification(command)
        }

        // 이벤트 발행
        eventPublisher.publishEvent(event)

        log.info{ "이메일 인증 시작됨 (email = ${event.data.email}"}

        // 처리 결과로 가공, 반환
        return makeResult(event)
    }

    private fun makeResult(event: EmailVerificationStartedEvent): EmailVerificationStartResult {
        return EmailVerificationStartResult(
            email = event.data.email,
            codeExpiresAt = event.data.codeExpiresAt,
        )
    }
}
