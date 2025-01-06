package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.BoardDescription
import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.board.domain.service.BoardManager
import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

@DomainService
class BoardManagerImpl : BoardManager {

    override fun create(
        name: BoardName,
        description: BoardDescription,
        managerId: MemberId,
        slug: BoardSlug,
        currentTime: ZonedDateTime
    ): Board {
        return Board.create(
            name = name,
            description = description,
            managerId = managerId,
            slug = slug,
            currentTime = currentTime,
        )
    }
}
