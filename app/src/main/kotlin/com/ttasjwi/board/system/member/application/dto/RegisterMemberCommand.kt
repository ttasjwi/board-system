package com.ttasjwi.board.system.member.application.dto

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.RawPassword

internal data class RegisterMemberCommand(
    val email: String,
    val rawPassword: RawPassword,
    val username: String,
    val nickname: String,
    val currentTime: AppDateTime,
)
