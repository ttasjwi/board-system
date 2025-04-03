package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.fixture.boardFixture
import com.ttasjwi.board.system.board.domain.service.BoardManager
import com.ttasjwi.board.system.common.time.AppDateTime
import java.util.concurrent.atomic.AtomicLong

class BoardManagerFixture : BoardManager {

    private val sequence: AtomicLong = AtomicLong(0)

    override fun create(
        name: String,
        description: String,
        managerId: Long,
        slug: String,
        currentTime: AppDateTime
    ): Board {
        return boardFixture(
            id = sequence.incrementAndGet(),
            name = name,
            description = description,
            managerId = managerId,
            slug = slug,
            createdAt = currentTime
        )
    }
}
