package com.ttasjwi.board.system.member.domain.port.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.port.EmailVerificationPersistencePort

class EmailVerificationPersistencePortFixture : EmailVerificationPersistencePort {

    private val store: MutableMap<String, EmailVerification> = mutableMapOf()

    override fun save(emailVerification: EmailVerification, expiresAt: AppDateTime) {
        store[emailVerification.email] = emailVerification
    }

    override fun remove(email: String) {
        store.remove(email)
    }

    override fun findByEmailOrNull(email: String): EmailVerification? {
        return store[email]
    }
}
