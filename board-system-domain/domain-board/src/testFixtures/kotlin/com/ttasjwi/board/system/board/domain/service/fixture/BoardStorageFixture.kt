package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.BoardId
import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.board.domain.model.fixture.boardIdFixture
import com.ttasjwi.board.system.board.domain.service.BoardStorage
import java.util.concurrent.atomic.AtomicLong

class BoardStorageFixture : BoardStorage {

    private val storage = mutableMapOf<BoardId, Board>()
    private val sequence = AtomicLong(0)

    override fun save(board: Board): Board {
        if (board.id == null) {
            val id = boardIdFixture(sequence.incrementAndGet())
            board.initId(id)
        }
        storage[board.id!!] = board
        return board
    }

    override fun findByIdOrNull(id: BoardId): Board? {
        return storage[id]
    }

    override fun existsByName(name: BoardName): Boolean {
        return storage.values.any { it.name == name }
    }

    override fun existsBySlug(slug: BoardSlug): Boolean {
        return storage.values.any { it.slug == slug }
    }

    override fun findBySlugOrNull(slug: BoardSlug): Board? {
        return storage.values.firstOrNull { it.slug == slug }
    }
}
