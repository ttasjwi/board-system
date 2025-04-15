package com.ttasjwi.board.system.board.domain.port.fixture

import com.ttasjwi.board.system.board.domain.model.BoardArticleCategory
import com.ttasjwi.board.system.board.domain.port.BoardArticleCategoryPersistencePort

class BoardArticleCategoryPersistencePortFixture : BoardArticleCategoryPersistencePort {

    private val storage = mutableMapOf<Long, BoardArticleCategory>()

    override fun save(boardArticleCategory: BoardArticleCategory): BoardArticleCategory {
        storage[boardArticleCategory.boardArticleCategoryId] = boardArticleCategory
        return boardArticleCategory
    }

    override fun findByIdOrNull(boardArticleCategoryId: Long): BoardArticleCategory? {
        return storage[boardArticleCategoryId]
    }

    override fun existsByName(boardId: Long, boardArticleCategoryName: String): Boolean {
        return storage.values.any { it.boardId == boardId && it.name == boardArticleCategoryName}
    }

    override fun existsBySlug(boardId: Long, boardArticleCategorySlug: String): Boolean {
        return storage.values.any { it.boardId == boardId && it.slug == boardArticleCategorySlug }
    }
}
