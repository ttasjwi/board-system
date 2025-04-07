package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.member.model.EmailVerification

interface EmailVerificationCreator {

    fun create(
        email: String,
        currentTime: AppDateTime,
    ): EmailVerification
}
