package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.common.annotation.component.ApplicationService
import com.ttasjwi.board.system.common.application.TransactionRunner
import com.ttasjwi.board.system.member.application.mapper.EmailVerificationCommandMapper
import com.ttasjwi.board.system.member.application.processor.EmailVerificationProcessor
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationResponse
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationUseCase
import com.ttasjwi.board.system.member.domain.event.EmailVerifiedEvent

@ApplicationService
internal class EmailVerificationApplicationService(
    private val commandMapper: EmailVerificationCommandMapper,
    private val processor: EmailVerificationProcessor,
    private val transactionRunner: TransactionRunner,
) : EmailVerificationUseCase {

    override fun emailVerification(request: EmailVerificationRequest): EmailVerificationResponse {
        val command = commandMapper.mapToCommand(request)

        val event = transactionRunner.run {
            processor.verify(command)
        }

        return makeResponse(event)
    }

    private fun makeResponse(event: EmailVerifiedEvent): EmailVerificationResponse {
        return EmailVerificationResponse(
            email = event.data.email,
            verificationExpiresAt = event.data.verificationExpiresAt.toZonedDateTime(),
        )
    }
}
