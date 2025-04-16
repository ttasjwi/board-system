package com.ttasjwi.board.system.article.domain.port

import com.ttasjwi.board.system.article.domain.model.ArticleCategory

interface ArticleCategoryPersistencePort {
    fun findByIdOrNull(articleCategoryId: Long): ArticleCategory?
}
