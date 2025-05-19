package com.ttasjwi.board.system.articlelike.domain

import java.time.ZonedDateTime

interface ArticleLikeCreateUseCase {
    fun like(articleId: Long): ArticleLikeCreateResponse
}

data class ArticleLikeCreateResponse(
    val articleLikeId: String,
    val articleId: String,
    val userId: String,
    val createdAt: ZonedDateTime,
)
