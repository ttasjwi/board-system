package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.BoardDescription
import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.board.domain.model.fixture.boardFixture
import com.ttasjwi.board.system.board.domain.service.BoardManager
import java.time.ZonedDateTime
import java.util.concurrent.atomic.AtomicLong

class BoardManagerFixture : BoardManager {

    private val sequence: AtomicLong = AtomicLong(0)

    override fun create(
        name: BoardName,
        description: BoardDescription,
        managerId: Long,
        slug: BoardSlug,
        currentTime: ZonedDateTime
    ): Board {
        return boardFixture(
            id = sequence.incrementAndGet(),
            name = name.value,
            description = description.value,
            managerId = managerId,
            slug = slug.value,
            createdAt = currentTime
        )
    }
}
