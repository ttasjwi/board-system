package com.ttasjwi.board.system.member.application.dto

import com.ttasjwi.board.system.member.domain.model.Email
import java.time.ZonedDateTime

internal data class EmailVerificationCommand(
    val email: Email,
    val code: String,
    val currentTime: ZonedDateTime,
)
