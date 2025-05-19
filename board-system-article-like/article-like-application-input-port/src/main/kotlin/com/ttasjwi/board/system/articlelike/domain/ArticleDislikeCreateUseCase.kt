package com.ttasjwi.board.system.articlelike.domain

import java.time.ZonedDateTime

interface ArticleDislikeCreateUseCase {
    fun dislike(articleId: Long): ArticleDislikeCreateResponse
}

data class ArticleDislikeCreateResponse(
    val articleDislikeId: String,
    val articleId: String,
    val userId: String,
    val createdAt: ZonedDateTime,
)
