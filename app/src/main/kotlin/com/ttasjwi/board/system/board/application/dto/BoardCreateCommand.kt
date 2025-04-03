package com.ttasjwi.board.system.board.application.dto

import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.common.time.AppDateTime

class BoardCreateCommand(
    val boardName: String,
    val boardDescription: String,
    val boardSlug: String,
    val creator: AuthMember,
    val currentTime: AppDateTime,
)
