package com.ttasjwi.board.system.member.domain.dto

import com.ttasjwi.board.system.common.time.AppDateTime

internal data class RegisterMemberCommand(
    val email: String,
    val rawPassword: String,
    val username: String,
    val nickname: String,
    val currentTime: AppDateTime,
)
