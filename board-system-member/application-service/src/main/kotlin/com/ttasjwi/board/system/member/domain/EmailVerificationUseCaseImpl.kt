package com.ttasjwi.board.system.member.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.member.domain.processor.EmailVerificationProcessor
import com.ttasjwi.board.system.member.domain.mapper.EmailVerificationCommandMapper
import com.ttasjwi.board.system.member.domain.model.EmailVerification

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
