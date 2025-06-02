package com.ttasjwi.board.system.article.domain

import java.time.ZonedDateTime

interface ArticleUpdateUseCase {
    fun update(articleId: Long, request: ArticleUpdateRequest): ArticleUpdateResponse
}

data class ArticleUpdateRequest(
    val title: String?,
    val content: String?,
)

data class ArticleUpdateResponse(
    val articleId: String,
    val title: String,
    val content: String,
    val boardId: String,
    val articleCategoryId: String,
    val writerId: String,
    val writerNickname: String,
    val createdAt: ZonedDateTime,
    val modifiedAt: ZonedDateTime,
)
