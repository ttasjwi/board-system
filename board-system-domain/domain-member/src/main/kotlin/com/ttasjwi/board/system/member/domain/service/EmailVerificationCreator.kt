package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import java.time.ZonedDateTime

interface EmailVerificationCreator {

    fun create(
        email: Email,
        currentTime: ZonedDateTime,
    ): EmailVerification
}
