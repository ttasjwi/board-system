package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.member.model.EmailVerification

interface EmailVerificationAppender {

    fun append(emailVerification: EmailVerification, expiresAt: AppDateTime)
    fun removeByEmail(email: String)
}
