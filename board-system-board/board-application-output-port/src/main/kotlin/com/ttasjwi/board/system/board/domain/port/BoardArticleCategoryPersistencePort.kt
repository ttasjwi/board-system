package com.ttasjwi.board.system.board.domain.port

import com.ttasjwi.board.system.board.domain.model.BoardArticleCategory

interface BoardArticleCategoryPersistencePort {

    fun save(boardArticleCategory: BoardArticleCategory): BoardArticleCategory
    fun findByIdOrNull(boardArticleCategoryId: Long): BoardArticleCategory?

    fun existsByName(boardId: Long, boardArticleCategoryName: String): Boolean
    fun existsBySlug(boardId: Long, boardArticleCategorySlug: String): Boolean
}
