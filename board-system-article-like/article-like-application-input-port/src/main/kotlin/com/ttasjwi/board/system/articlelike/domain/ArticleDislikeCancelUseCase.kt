package com.ttasjwi.board.system.articlelike.domain

import java.time.ZonedDateTime

interface ArticleDislikeCancelUseCase {
    fun cancelDislike(articleId: Long): ArticleDislikeCancelResponse
}

data class ArticleDislikeCancelResponse(
    val articleId: String,
    val userId: String,
    val canceledAt: ZonedDateTime,
)
