package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.user.domain.dto.EmailVerificationCommand
import com.ttasjwi.board.system.user.domain.exception.EmailVerificationNotFoundException
import com.ttasjwi.board.system.user.domain.model.EmailVerification
import com.ttasjwi.board.system.user.domain.port.EmailVerificationPersistencePort

@ApplicationProcessor
internal class EmailVerificationProcessor(
    private val emailVerificationPersistencePort: EmailVerificationPersistencePort,
) {

    fun verify(command: EmailVerificationCommand): EmailVerification {
        val emailVerification = getEmailVerification(command)
        val verifiedEmailVerification = emailVerification.codeVerify(command.code, command.currentTime)
        emailVerificationPersistencePort.save(
            verifiedEmailVerification,
            verifiedEmailVerification.verificationExpiresAt!!
        )
        return verifiedEmailVerification
    }

    private fun getEmailVerification(command: EmailVerificationCommand): EmailVerification {
        return emailVerificationPersistencePort.findByEmailOrNull(command.email)
            ?: throw EmailVerificationNotFoundException(command.email)
    }
}
