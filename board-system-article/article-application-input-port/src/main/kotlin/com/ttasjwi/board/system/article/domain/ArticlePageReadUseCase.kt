package com.ttasjwi.board.system.article.domain

import java.time.ZonedDateTime

interface ArticlePageReadUseCase {

    fun readAllPage(request: ArticlePageReadRequest): ArticlePageReadResponse
}

data class ArticlePageReadRequest(
    val boardId: Long?,
    val page: Long?,
    val pageSize: Long?
)

data class ArticlePageReadResponse(
    val page: Long,
    val pageSize: Long,
    val pageGroupSize: Long,
    val pageGroupStart: Long,
    val pageGroupEnd: Long,
    val hasNextPage: Boolean,
    val hasNextPageGroup: Boolean,
    val articles: List<ArticlePageElement>,
)

data class ArticlePageElement(
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
