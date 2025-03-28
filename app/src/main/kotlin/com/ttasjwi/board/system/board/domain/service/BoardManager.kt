package com.ttasjwi.board.system.board.domain.service

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.BoardDescription
import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

interface BoardManager {

    fun create(
        name: BoardName,
        description: BoardDescription,
        managerId: MemberId,
        slug: BoardSlug,
        currentTime: ZonedDateTime,
    ): Board
}
