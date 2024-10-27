package com.ttasjwi.board.system.email.domain.external.redis

import com.ttasjwi.board.system.core.annotation.component.AppComponent
import com.ttasjwi.board.system.email.domain.model.Email
import com.ttasjwi.board.system.email.domain.model.EmailVerification
import com.ttasjwi.board.system.email.domain.service.EmailVerificationAppender
import com.ttasjwi.board.system.email.domain.service.EmailVerificationFinder
import java.time.ZonedDateTime

@AppComponent
internal class EmailVerificationStorage : EmailVerificationAppender, EmailVerificationFinder {

    override fun append(emailVerification: EmailVerification, expiresAt: ZonedDateTime) {
        TODO("Not yet implemented")
    }

    override fun removeByEmail(email: Email) {
        TODO("Not yet implemented")
    }

    override fun findByEmailOrNull(email: Email): EmailVerification? {
        TODO("Not yet implemented")
    }
}
