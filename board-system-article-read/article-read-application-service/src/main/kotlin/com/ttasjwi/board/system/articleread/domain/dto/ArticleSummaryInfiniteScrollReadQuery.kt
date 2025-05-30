package com.ttasjwi.board.system.articleread.domain.dto

internal data class ArticleSummaryInfiniteScrollReadQuery(
    val boardId: Long,
    val pageSize: Long,
    val lastArticleId: Long?
)
