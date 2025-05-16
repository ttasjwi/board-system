package com.ttasjwi.board.system.articlecomment.domain

import java.time.ZonedDateTime

interface ArticleCommentInfiniteScrollReadUseCase {
    fun readAllInfiniteScroll(request: ArticleCommentInfiniteScrollReadRequest): ArticleCommentInfiniteScrollReadResponse
}

data class ArticleCommentInfiniteScrollReadRequest(
    val articleId: Long?,
    val pageSize: Long?,
    val lastRootParentCommentId: Long?,
    val lastCommentId: Long?,
)

data class ArticleCommentInfiniteScrollReadResponse(
    val comments: List<CommentItem>,
    val hasNext: Boolean,
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
