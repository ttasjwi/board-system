package com.ttasjwi.board.system.application.member.service

import com.ttasjwi.board.system.application.member.mapper.EmailVerificationStartCommandMapper
import com.ttasjwi.board.system.application.member.processor.EmailVerificationStartProcessor
import com.ttasjwi.board.system.application.member.usecase.EmailVerificationStartRequest
import com.ttasjwi.board.system.application.member.usecase.EmailVerificationStartResponse
import com.ttasjwi.board.system.application.member.usecase.EmailVerificationStartUseCase
import com.ttasjwi.board.system.domain.member.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.domain.member.service.EmailVerificationStartedEventPublisher
import com.ttasjwi.board.system.global.annotation.ApplicationService
import com.ttasjwi.board.system.global.logging.getLogger

@ApplicationService
internal class EmailVerificationStartApplicationService(
    private val commandMapper: EmailVerificationStartCommandMapper,
    private val processor: EmailVerificationStartProcessor,
    private val eventPublisher: EmailVerificationStartedEventPublisher
) : EmailVerificationStartUseCase {

    companion object {
        private val log = getLogger(EmailVerificationStartApplicationService::class.java)
    }

    override fun startEmailVerification(request: EmailVerificationStartRequest): EmailVerificationStartResponse {
        log.info { "이메일 인증 시작 요청을 받았습니다." }

        // 유효성 검사를 거쳐서 명령으로 변환
        val command = commandMapper.mapToCommand(request)

        // 프로세서에 위임
        val event = processor.startEmailVerification(command)

        // 이벤트 발행
        eventPublisher.publishEvent(event)

        log.info { "이메일 인증 시작됨 (email = ${event.data.email}" }

        // 응답 가공, 반환
        return makeResponse(event)
    }

    private fun makeResponse(event: EmailVerificationStartedEvent): EmailVerificationStartResponse {
        return EmailVerificationStartResponse(
            email = event.data.email,
            codeExpiresAt = event.data.codeExpiresAt.toZonedDateTime(),
        )
    }
}
