package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.board.domain.service.BoardStorage

class BoardStorageFixture : BoardStorage {

    private val storage = mutableMapOf<Long, Board>()

    override fun save(board: Board): Board {
        storage[board.id] = board
        return board
    }

    override fun findByIdOrNull(id: Long): Board? {
        return storage[id]
    }

    override fun existsByName(name: String): Boolean {
        return storage.values.any { it.name == name }
    }

    override fun existsBySlug(slug: BoardSlug): Boolean {
        return storage.values.any { it.slug == slug }
    }

    override fun findBySlugOrNull(slug: BoardSlug): Board? {
        return storage.values.firstOrNull { it.slug == slug }
    }
}
