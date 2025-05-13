package com.ttasjwi.board.system.articlecomment.domain

import java.time.ZonedDateTime

interface ArticleCommentCreateUseCase {
    fun create(request: ArticleCommentCreateRequest): ArticleCommentCreateResponse
}

data class ArticleCommentCreateRequest(
    val content: String?,
    val articleId: Long?,
    val parentCommentId: Long?,
)

data class ArticleCommentCreateResponse(
    val articleCommentId: String,
    val content: String,
    val articleId: String,
    val rootParentCommentId: String,
    val writerId: String,
    val writerNickname: String,
    val parentCommentWriterId: String?,
    val parentCommentWriterNickname: String?,
    val deleteStatus: String,
    val createdAt: ZonedDateTime,
    val modifiedAt: ZonedDateTime,
)
