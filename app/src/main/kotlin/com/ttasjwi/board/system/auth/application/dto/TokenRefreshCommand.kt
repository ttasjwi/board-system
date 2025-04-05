package com.ttasjwi.board.system.auth.application.dto

import com.ttasjwi.board.system.global.time.AppDateTime

internal class TokenRefreshCommand(
    val refreshToken: String,
    val currentTime: AppDateTime,
)
