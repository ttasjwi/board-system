package com.ttasjwi.board.system.board.application.dto

import com.ttasjwi.board.system.board.domain.model.BoardDescription
import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.common.time.AppDateTime

class BoardCreateCommand(
    val boardName: BoardName,
    val boardDescription: BoardDescription,
    val boardSlug: BoardSlug,
    val creator: AuthMember,
    val currentTime: AppDateTime,
)
