package com.ttasjwi.board.system.member.domain.port

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.EmailVerification

interface EmailVerificationPersistencePort {

    fun save(emailVerification: EmailVerification, expiresAt: AppDateTime)
    fun findByEmailOrNull(email: String): EmailVerification?
    fun remove(email: String)
}
