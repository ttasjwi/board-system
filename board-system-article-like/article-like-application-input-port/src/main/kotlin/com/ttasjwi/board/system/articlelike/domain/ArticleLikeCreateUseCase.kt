package com.ttasjwi.board.system.articlelike.domain

import java.time.ZonedDateTime

interface ArticleLikeCreateUseCase {
    fun like(request: ArticleLikeCreateRequest): ArticleLikeCreateResponse
}

data class ArticleLikeCreateRequest(
    val articleId: Long?,
)

data class ArticleLikeCreateResponse(
    val articleLikeId: String,
    val articleId: String,
    val userId: String,
    val createdAt: ZonedDateTime,
)
