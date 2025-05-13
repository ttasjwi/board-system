package com.ttasjwi.board.system.articlecomment.domain

import java.time.ZonedDateTime

interface ArticleCommentReadUseCase {
    fun read(commentId: Long): ArticleCommentReadResponse
}

data class ArticleCommentReadResponse(
    val articleCommentId: String,
    val content: String,
    val articleId: String,
    val rootParentCommentId: String,
    val writerId: String,
    val writerNickname: String,
    val parentCommentWriterId: String?,
    val parentCommentWriterNickname: String?,
    val createdAt: ZonedDateTime,
    val modifiedAt: ZonedDateTime,
)
