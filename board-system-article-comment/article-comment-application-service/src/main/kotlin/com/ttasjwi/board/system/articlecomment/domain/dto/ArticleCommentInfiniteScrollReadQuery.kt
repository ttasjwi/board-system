package com.ttasjwi.board.system.articlecomment.domain.dto

data class ArticleCommentInfiniteScrollReadQuery(
    val articleId: Long,
    val pageSize: Long,
    val lastRootParentCommentId: Long?,
    val lastCommentId: Long?,
)
