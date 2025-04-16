package com.ttasjwi.board.system.board.domain.port

import com.ttasjwi.board.system.board.domain.model.ArticleCategory

interface ArticleCategoryPersistencePort {

    fun save(articleCategory: ArticleCategory): ArticleCategory
    fun findByIdOrNull(articleCategoryId: Long): ArticleCategory?

    fun existsByName(boardId: Long, articleCategoryName: String): Boolean
    fun existsBySlug(boardId: Long, articleCategorySlug: String): Boolean
}
