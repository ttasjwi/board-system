package com.ttasjwi.board.system.articlecomment.domain

import java.time.ZonedDateTime

interface ArticleCommentPageReadUseCase {
    fun readAllPage(request: ArticleCommentPageReadRequest): ArticleCommentPageReadResponse
}

data class ArticleCommentPageReadRequest(
    val articleId: Long?,
    val page: Long?,
    val pageSize: Long?
)

data class ArticleCommentPageReadResponse(
    val page: Long,
    val pageSize: Long,
    val pageGroupSize: Long,
    val pageGroupStart: Long,
    val pageGroupEnd: Long,
    val hasNextPage: Boolean,
    val hasNextPageGroup: Boolean,
    val comments: List<CommentItem>,
) {

    data class CommentItem(
        val deleteStatus: String,
        val data: Data?,
    ) {
        data class Data(
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
    }
}
