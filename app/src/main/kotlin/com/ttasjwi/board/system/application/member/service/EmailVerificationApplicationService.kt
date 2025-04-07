package com.ttasjwi.board.system.application.member.service

import com.ttasjwi.board.system.application.member.mapper.EmailVerificationCommandMapper
import com.ttasjwi.board.system.application.member.processor.EmailVerificationProcessor
import com.ttasjwi.board.system.application.member.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.application.member.usecase.EmailVerificationResponse
import com.ttasjwi.board.system.application.member.usecase.EmailVerificationUseCase
import com.ttasjwi.board.system.domain.member.event.EmailVerifiedEvent
import com.ttasjwi.board.system.global.annotation.ApplicationService

@ApplicationService
internal class EmailVerificationApplicationService(
    private val commandMapper: EmailVerificationCommandMapper,
    private val processor: EmailVerificationProcessor,
) : EmailVerificationUseCase {

    override fun emailVerification(request: EmailVerificationRequest): EmailVerificationResponse {
        val command = commandMapper.mapToCommand(request)
        val event = processor.verify(command)
        return makeResponse(event)
    }

    private fun makeResponse(event: EmailVerifiedEvent): EmailVerificationResponse {
        return EmailVerificationResponse(
            email = event.data.email,
            verificationExpiresAt = event.data.verificationExpiresAt.toZonedDateTime(),
        )
    }
}
