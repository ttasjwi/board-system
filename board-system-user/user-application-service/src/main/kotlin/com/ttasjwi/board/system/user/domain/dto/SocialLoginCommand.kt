package com.ttasjwi.board.system.user.domain.dto

import com.ttasjwi.board.system.common.time.AppDateTime

data class SocialLoginCommand(
    val state: String,
    val code: String,
    val currentTime: AppDateTime,
)
