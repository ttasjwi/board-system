package com.ttasjwi.board.system.auth.application.dto

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.RawPassword

class LoginCommand(
    val email: Email,
    val rawPassword: RawPassword,
    val currentTime: AppDateTime,
)
