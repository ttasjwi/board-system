package com.ttasjwi.board.system.board.domain.service

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.common.time.AppDateTime

interface BoardManager {

    fun create(
        name: String,
        description: String,
        managerId: Long,
        slug: String,
        currentTime: AppDateTime,
    ): Board
}
