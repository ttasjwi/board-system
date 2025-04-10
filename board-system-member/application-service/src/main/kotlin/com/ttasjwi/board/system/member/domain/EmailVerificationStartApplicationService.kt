package com.ttasjwi.board.system.member.domain

import com.ttasjwi.board.system.common.annotation.component.ApplicationService
import com.ttasjwi.board.system.member.domain.mapper.EmailVerificationStartCommandMapper
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.processor.EmailVerificationStartProcessor

@ApplicationService
internal class EmailVerificationStartApplicationService(
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
