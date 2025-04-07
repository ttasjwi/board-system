package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.model.EmailVerification
import com.ttasjwi.board.system.global.time.AppDateTime

interface EmailVerificationCreator {

    fun create(
        email: String,
        currentTime: AppDateTime,
    ): EmailVerification
}
