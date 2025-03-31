package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.BoardDescription
import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.board.domain.service.BoardManager
import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.idgerator.IdGenerator
import java.time.ZonedDateTime

@DomainService
class BoardManagerImpl : BoardManager {

    private val idGenerator: IdGenerator = IdGenerator.create()

    override fun create(
        name: BoardName,
        description: BoardDescription,
        managerId: Long,
        slug: BoardSlug,
        currentTime: ZonedDateTime
    ): Board {
        return Board.create(
            id = idGenerator.nextId(),
            name = name,
            description = description,
            managerId = managerId,
            slug = slug,
            currentTime = currentTime,
        )
    }
}
