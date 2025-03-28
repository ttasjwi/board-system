package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import java.time.ZonedDateTime

interface EmailVerificationAppender {

    fun append(emailVerification: EmailVerification, expiresAt: ZonedDateTime)
    fun removeByEmail(email: Email)
}
