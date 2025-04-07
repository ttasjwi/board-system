package com.ttasjwi.board.system.application.board.dto

import com.ttasjwi.board.system.global.auth.AuthMember
import com.ttasjwi.board.system.global.time.AppDateTime

class BoardCreateCommand(
    val boardName: String,
    val boardDescription: String,
    val boardSlug: String,
    val creator: AuthMember,
    val currentTime: AppDateTime,
)
