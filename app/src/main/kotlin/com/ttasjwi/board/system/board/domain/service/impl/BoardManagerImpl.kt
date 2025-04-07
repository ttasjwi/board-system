package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.service.BoardManager
import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import com.ttasjwi.board.system.common.time.AppDateTime

@DomainService
class BoardManagerImpl : BoardManager {

    private val idGenerator: IdGenerator = IdGenerator.create()

    override fun create(
        name: String,
        description: String,
        managerId: Long,
        slug: String,
        currentTime: AppDateTime
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
