package com.ttasjwi.board.system.board.domain.dto

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.time.AppDateTime

class BoardCreateCommand(
    val boardName: String,
    val boardDescription: String,
    val boardSlug: String,
    val creator: AuthUser,
    val currentTime: AppDateTime,
)
