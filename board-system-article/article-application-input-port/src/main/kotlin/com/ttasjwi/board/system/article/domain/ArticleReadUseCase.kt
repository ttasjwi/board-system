package com.ttasjwi.board.system.article.domain

import java.time.ZonedDateTime

interface ArticleReadUseCase {
    fun read(articleId: Long): ArticleReadResponse
}

data class ArticleReadResponse(
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
