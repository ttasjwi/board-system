package com.ttasjwi.board.system.member.application.dto

import com.ttasjwi.board.system.common.time.AppDateTime

internal data class EmailVerificationCommand(
    val email: String,
    val code: String,
    val currentTime: AppDateTime,
)
