package com.ttasjwi.board.system.article.domain.dto

data class ArticleInfiniteScrollReadQuery(
    val boardId: Long,
    val pageSize: Long,
    val lastArticleId: Long?
)
