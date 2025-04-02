package com.ttasjwi.board.system.member.application.dto

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.Email

internal data class EmailVerificationCommand(
    val email: Email,
    val code: String,
    val currentTime: AppDateTime,
)
