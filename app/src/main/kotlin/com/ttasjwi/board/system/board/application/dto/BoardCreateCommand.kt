package com.ttasjwi.board.system.board.application.dto

import com.ttasjwi.board.system.board.domain.model.BoardDescription
import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import java.time.ZonedDateTime

class BoardCreateCommand(
    val boardName: BoardName,
    val boardDescription: BoardDescription,
    val boardSlug: BoardSlug,
    val creator: AuthMember,
    val currentTime: ZonedDateTime,
)
