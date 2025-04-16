package com.ttasjwi.board.system.user.domain.dto

import com.ttasjwi.board.system.common.time.AppDateTime

class LoginCommand(
    val email: String,
    val rawPassword: String,
    val currentTime: AppDateTime,
)
