package com.ttasjwi.board.system.board.domain.port.fixture

import com.ttasjwi.board.system.board.domain.model.ArticleCategory
import com.ttasjwi.board.system.board.domain.port.ArticleCategoryPersistencePort

class ArticleCategoryPersistencePortFixture : ArticleCategoryPersistencePort {

    private val storage = mutableMapOf<Long, ArticleCategory>()

    override fun save(articleCategory: ArticleCategory): ArticleCategory {
        storage[articleCategory.articleCategoryId] = articleCategory
        return articleCategory
    }

    override fun findByIdOrNull(articleCategoryId: Long): ArticleCategory? {
        return storage[articleCategoryId]
    }

    override fun existsByName(boardId: Long, articleCategoryName: String): Boolean {
        return storage.values.any { it.boardId == boardId && it.name == articleCategoryName}
    }

    override fun existsBySlug(boardId: Long, articleCategorySlug: String): Boolean {
        return storage.values.any { it.boardId == boardId && it.slug == articleCategorySlug }
    }
}
