package com.ttasjwi.board.system.article.domain

import java.time.ZonedDateTime

interface ArticleInfiniteScrollReadUseCase {
    fun readAllInfiniteScroll(request: ArticleInfiniteScrollReadRequest): ArticleInfiniteScrollReadResponse
}

data class ArticleInfiniteScrollReadRequest(
    val boardId: Long?,
    val pageSize: Long?,
    val lastArticleId: Long?,
)

data class ArticleInfiniteScrollReadResponse(
    val articles: List<ArticleInfiniteScrollItem>,
    val hasNext: Boolean,
)

data class ArticleInfiniteScrollItem(
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
