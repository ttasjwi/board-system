package com.ttasjwi.board.system.email.domain.service

import com.ttasjwi.board.system.email.domain.model.Email
import com.ttasjwi.board.system.email.domain.model.EmailVerification
import java.time.ZonedDateTime

interface EmailVerificationAppender {

    fun append(emailVerification: EmailVerification, expiresAt: ZonedDateTime)
    fun removeByEmail(email: Email)
}
