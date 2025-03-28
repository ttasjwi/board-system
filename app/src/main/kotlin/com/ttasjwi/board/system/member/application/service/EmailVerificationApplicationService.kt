package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.common.annotation.component.ApplicationService
import com.ttasjwi.board.system.common.application.TransactionRunner
import com.ttasjwi.board.system.member.application.mapper.EmailVerificationCommandMapper
import com.ttasjwi.board.system.member.application.processor.EmailVerificationProcessor
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationResult
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationUseCase
import com.ttasjwi.board.system.member.domain.event.EmailVerifiedEvent

@ApplicationService
internal class EmailVerificationApplicationService(
    private val commandMapper: EmailVerificationCommandMapper,
    private val processor: EmailVerificationProcessor,
    private val transactionRunner: TransactionRunner,
) : EmailVerificationUseCase {

    override fun emailVerification(request: EmailVerificationRequest): EmailVerificationResult {
        val command = commandMapper.mapToCommand(request)

        val event = transactionRunner.run {
            processor.verify(command)
        }

        return mapToResult(event)
    }

    private fun mapToResult(event: EmailVerifiedEvent): EmailVerificationResult {
        return EmailVerificationResult(
            email = event.data.email,
            verificationExpiresAt = event.data.verificationExpiresAt,
        )
    }
}
