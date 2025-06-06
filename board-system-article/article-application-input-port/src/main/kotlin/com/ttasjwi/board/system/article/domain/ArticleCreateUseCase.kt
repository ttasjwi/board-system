package com.ttasjwi.board.system.article.domain

import java.time.ZonedDateTime

interface ArticleCreateUseCase {
    fun create(request: ArticleCreateRequest): ArticleCreateResponse
}

data class ArticleCreateRequest(
    val title: String?,
    val content: String?,
    val articleCategoryId: Long?,
)

data class ArticleCreateResponse(
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
