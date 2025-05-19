package com.ttasjwi.board.system.articlelike.domain.port

import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislikeCount

interface ArticleDislikeCountPersistencePort {
    fun save(articleDislikeCount: ArticleDislikeCount): ArticleDislikeCount
    fun findByIdOrNull(articleId: Long): ArticleDislikeCount?
}
