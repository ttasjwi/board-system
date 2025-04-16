package com.ttasjwi.board.system.article.domain.port.fixture

import com.ttasjwi.board.system.article.domain.model.ArticleCategory
import com.ttasjwi.board.system.article.domain.port.ArticleCategoryPersistencePort

class ArticleCategoryPersistencePortFixture : ArticleCategoryPersistencePort {

    private val storage = mutableMapOf<Long, ArticleCategory>()

    fun save(articleCategory: ArticleCategory): ArticleCategory {
        storage[articleCategory.articleCategoryId] = articleCategory
        return articleCategory
    }

    override fun findByIdOrNull(articleCategoryId: Long): ArticleCategory? {
        return storage[articleCategoryId]
    }
}
