package com.ttasjwi.board.system.auth.application.dto

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.RawPassword

class LoginCommand(
    val email: String,
    val rawPassword: RawPassword,
    val currentTime: AppDateTime,
)
