package com.ttasjwi.board.system.application.auth.dto

import com.ttasjwi.board.system.global.time.AppDateTime

internal class TokenRefreshCommand(
    val refreshToken: String,
    val currentTime: AppDateTime,
)
