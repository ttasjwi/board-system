package com.ttasjwi.board.system.articleread.domain

import java.time.ZonedDateTime

interface ArticleSummaryInfiniteScrollReadUseCase {
    fun readAllInfiniteScroll(request: ArticleSummaryInfiniteScrollReadRequest): ArticleSummaryInfiniteScrollReadResponse
}

data class ArticleSummaryInfiniteScrollReadRequest(
    val boardId: Long,
    val pageSize: Long?,
    val lastArticleId: Long?,
)

data class ArticleSummaryInfiniteScrollReadResponse(
    val articles: List<Article>,
    val hasNext: Boolean,
) {

    data class Article(
        val articleId: String,
        val title: String,
        val board: Board,
        val articleCategory: ArticleCategory,
        val writer: Writer,
        val viewCount: Long,
        val commentCount: Long,
        val likeCount: Long,
        val dislikeCount: Long,
        val createdAt: ZonedDateTime,
    ) {

        data class Board(
            val boardId: String,
            val name: String,
            val slug: String,
        )

        data class ArticleCategory(
            val articleCategoryId: String,
            val name: String,
            val slug: String,
        )

        data class Writer(
            val writerId: String,
            val nickname: String, // 작성시점 닉네임
        )
    }
}
