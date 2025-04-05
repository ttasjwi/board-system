package com.ttasjwi.board.system.member.application.dto

import com.ttasjwi.board.system.global.time.AppDateTime

internal data class EmailVerificationCommand(
    val email: String,
    val code: String,
    val currentTime: AppDateTime,
)
