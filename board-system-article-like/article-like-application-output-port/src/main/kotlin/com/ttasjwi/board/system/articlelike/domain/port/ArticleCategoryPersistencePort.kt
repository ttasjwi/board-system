package com.ttasjwi.board.system.articlelike.domain.port

import com.ttasjwi.board.system.articlelike.domain.model.ArticleCategory

interface ArticleCategoryPersistencePort {
    fun findByIdOrNull(articleCategoryId: Long): ArticleCategory?
}
