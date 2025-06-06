package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.user.domain.mapper.EmailVerificationStartCommandMapper
import com.ttasjwi.board.system.user.domain.model.EmailVerification
import com.ttasjwi.board.system.user.domain.processor.EmailVerificationStartProcessor

@UseCase
internal class EmailVerificationStartUseCaseImpl(
    private val commandMapper: EmailVerificationStartCommandMapper,
    private val processor: EmailVerificationStartProcessor,
) : EmailVerificationStartUseCase {

    override fun startEmailVerification(request: EmailVerificationStartRequest): EmailVerificationStartResponse {
        val command = commandMapper.mapToCommand(request)
        val emailVerification = processor.startEmailVerification(command)
        return makeResponse(emailVerification)
    }

    private fun makeResponse(emailVerification: EmailVerification): EmailVerificationStartResponse {
        return EmailVerificationStartResponse(
            email = emailVerification.email,
            codeExpiresAt = emailVerification.codeExpiresAt.toZonedDateTime(),
        )
    }
}
