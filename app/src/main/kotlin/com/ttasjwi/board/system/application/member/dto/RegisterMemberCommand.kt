package com.ttasjwi.board.system.application.member.dto

import com.ttasjwi.board.system.global.time.AppDateTime

internal data class RegisterMemberCommand(
    val email: String,
    val rawPassword: String,
    val username: String,
    val nickname: String,
    val currentTime: AppDateTime,
)
