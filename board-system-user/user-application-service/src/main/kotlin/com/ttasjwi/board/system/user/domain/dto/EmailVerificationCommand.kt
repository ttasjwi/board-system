package com.ttasjwi.board.system.user.domain.dto

import com.ttasjwi.board.system.common.time.AppDateTime

internal data class EmailVerificationCommand(
    val email: String,
    val code: String,
    val currentTime: AppDateTime,
)
