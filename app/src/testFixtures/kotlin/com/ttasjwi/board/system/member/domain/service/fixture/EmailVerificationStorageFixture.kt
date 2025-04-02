package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.service.EmailVerificationAppender
import com.ttasjwi.board.system.member.domain.service.EmailVerificationFinder

class EmailVerificationStorageFixture : EmailVerificationAppender, EmailVerificationFinder  {

    private val store: MutableMap<Email, EmailVerification> = mutableMapOf()

    override fun append(emailVerification: EmailVerification, expiresAt: AppDateTime) {
        store[emailVerification.email] = emailVerification
    }

    override fun removeByEmail(email: Email) {
        store.remove(email)
    }

    override fun findByEmailOrNull(email: Email): EmailVerification? {
        return store[email]
    }
}
