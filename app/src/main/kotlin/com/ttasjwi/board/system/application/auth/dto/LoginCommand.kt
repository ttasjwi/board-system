package com.ttasjwi.board.system.application.auth.dto

import com.ttasjwi.board.system.global.time.AppDateTime

class LoginCommand(
    val email: String,
    val rawPassword: String,
    val currentTime: AppDateTime,
)
