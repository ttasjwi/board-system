package com.ttasjwi.board.system.articlelike.domain.port

import com.ttasjwi.board.system.articlelike.domain.model.ArticleLike

interface ArticleLikePersistencePort {
    fun save(articleLike: ArticleLike): ArticleLike
    fun findByIdOrNullTest(articleLikeId: Long): ArticleLike?
    fun findByArticleIdAndUserIdOrNull(articleId: Long, userId: Long): ArticleLike?
    fun existsByArticleIdAndUserId(articleId: Long, userId: Long): Boolean
    fun remove(articleId: Long, userId: Long)
}
