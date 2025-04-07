package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.model.EmailVerification
import com.ttasjwi.board.system.global.time.AppDateTime

interface EmailVerificationAppender {

    fun append(emailVerification: EmailVerification, expiresAt: AppDateTime)
    fun removeByEmail(email: String)
}
