package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.AppComponent
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.service.EmailVerificationAppender
import com.ttasjwi.board.system.member.domain.service.EmailVerificationFinder
import java.time.ZonedDateTime
import java.util.concurrent.ConcurrentHashMap

@AppComponent
internal class EmailVerificationStorage : EmailVerificationAppender, EmailVerificationFinder {

    private val store: MutableMap<Email, EmailVerification> = ConcurrentHashMap()

    override fun append(emailVerification: EmailVerification, expiresAt: ZonedDateTime) {
        store[emailVerification.email] = emailVerification
    }

    override fun removeByEmail(email: Email) {
        store.remove(email)
    }

    override fun findByEmailOrNull(email: Email): EmailVerification? {
        return store[email]
    }
}
