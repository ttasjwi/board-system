package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.user.domain.mapper.EmailVerificationCommandMapper
import com.ttasjwi.board.system.user.domain.model.EmailVerification
import com.ttasjwi.board.system.user.domain.processor.EmailVerificationProcessor

@UseCase
internal class EmailVerificationUseCaseImpl(
    private val commandMapper: EmailVerificationCommandMapper,
    private val processor: EmailVerificationProcessor,
) : EmailVerificationUseCase {

    override fun emailVerification(request: EmailVerificationRequest): EmailVerificationResponse {
        val command = commandMapper.mapToCommand(request)
        val emailVerification = processor.verify(command)
        return makeResponse(emailVerification)
    }

    private fun makeResponse(emailVerification: EmailVerification): EmailVerificationResponse {
        return EmailVerificationResponse(
            email = emailVerification.email,
            verificationExpiresAt = emailVerification.verificationExpiresAt!!.toZonedDateTime(),
        )
    }
}
