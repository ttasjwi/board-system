package com.ttasjwi.board.system.board.domain.service

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.BoardDescription
import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.common.time.AppDateTime

interface BoardManager {

    fun create(
        name: BoardName,
        description: BoardDescription,
        managerId: Long,
        slug: BoardSlug,
        currentTime: AppDateTime,
    ): Board
}
