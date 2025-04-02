package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.EmailVerification

interface EmailVerificationCreator {

    fun create(
        email: Email,
        currentTime: AppDateTime,
    ): EmailVerification
}
