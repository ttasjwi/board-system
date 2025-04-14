package com.ttasjwi.board.system.member.domain.dto

import com.ttasjwi.board.system.common.time.AppDateTime

internal class TokenRefreshCommand(
    val refreshToken: String,
    val currentTime: AppDateTime,
)
