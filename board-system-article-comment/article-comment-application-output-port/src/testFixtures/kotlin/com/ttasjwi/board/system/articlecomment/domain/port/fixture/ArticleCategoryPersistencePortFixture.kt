package com.ttasjwi.board.system.articlecomment.domain.port.fixture

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCategory
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCategoryPersistencePort

class ArticleCategoryPersistencePortFixture : ArticleCategoryPersistencePort {

    private val storage = mutableMapOf<Long, ArticleCategory>()

    fun save(articleCategory: ArticleCategory) {
        storage[articleCategory.articleCategoryId] = articleCategory
    }

    override fun findByIdOrNull(articleCategoryId: Long): ArticleCategory? {
        return storage[articleCategoryId]
    }
}
