package com.ttasjwi.board.system.articlelike.domain

import java.time.ZonedDateTime

interface ArticleLikeCancelUseCase {
    fun cancelLike(articleId: Long): ArticleLikeCancelResponse
}

data class ArticleLikeCancelResponse(
    val articleId: String,
    val userId: String,
    val canceledAt: ZonedDateTime,
)
