package com.ttasjwi.board.system.articlelike.domain.port

import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislike

interface ArticleDislikePersistencePort {
    fun save(articleDislike: ArticleDislike): ArticleDislike
    fun findByIdOrNullTest(articleDislikeId: Long): ArticleDislike?
    fun findByArticleIdAndUserIdOrNull(articleId: Long, userId: Long): ArticleDislike?
    fun existsByArticleIdAndUserId(articleId: Long, userId: Long): Boolean
}
