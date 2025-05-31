package com.ttasjwi.board.system.articlecomment.domain.port

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCategory

interface ArticleCategoryPersistencePort {
    fun findByIdOrNull(articleCategoryId: Long): ArticleCategory?
}
