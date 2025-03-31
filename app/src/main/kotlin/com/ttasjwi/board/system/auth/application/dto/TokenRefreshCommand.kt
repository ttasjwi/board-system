package com.ttasjwi.board.system.auth.application.dto

import java.time.ZonedDateTime

internal class TokenRefreshCommand(
    val refreshToken: String,
    val currentTime: ZonedDateTime,
)
