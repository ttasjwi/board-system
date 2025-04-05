package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.service.EmailVerificationAppender
import com.ttasjwi.board.system.member.domain.service.EmailVerificationFinder

class EmailVerificationStorageFixture : EmailVerificationAppender, EmailVerificationFinder {

    private val store: MutableMap<String, EmailVerification> = mutableMapOf()

    override fun append(emailVerification: EmailVerification, expiresAt: AppDateTime) {
        store[emailVerification.email] = emailVerification
    }

    override fun removeByEmail(email: String) {
        store.remove(email)
    }

    override fun findByEmailOrNull(email: String): EmailVerification? {
        return store[email]
    }
}
