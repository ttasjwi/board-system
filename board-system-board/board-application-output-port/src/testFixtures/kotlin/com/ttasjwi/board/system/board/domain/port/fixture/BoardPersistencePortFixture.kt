package com.ttasjwi.board.system.board.domain.port.fixture

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.port.BoardPersistencePort

class BoardPersistencePortFixture : BoardPersistencePort {

    private val storage = mutableMapOf<Long, Board>()

    override fun save(board: Board): Board {
        storage[board.boardId] = board
        return board
    }

    override fun findByIdOrNull(id: Long): Board? {
        return storage[id]
    }

    override fun existsByName(name: String): Boolean {
        return storage.values.any { it.name == name }
    }

    override fun existsBySlug(slug: String): Boolean {
        return storage.values.any { it.slug == slug }
    }

    override fun findBySlugOrNull(slug: String): Board? {
        return storage.values.firstOrNull { it.slug == slug }
    }
}
