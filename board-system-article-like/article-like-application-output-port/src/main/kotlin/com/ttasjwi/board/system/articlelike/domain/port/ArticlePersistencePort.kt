package com.ttasjwi.board.system.articlelike.domain.port

import com.ttasjwi.board.system.articlelike.domain.model.Article

interface ArticlePersistencePort {
    fun findByIdOrNull(articleId: Long): Article?
    fun existsByArticleId(articleId: Long): Boolean
}
