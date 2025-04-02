package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.EmailVerification

interface EmailVerificationAppender {

    fun append(emailVerification: EmailVerification, expiresAt: AppDateTime)
    fun removeByEmail(email: Email)
}
