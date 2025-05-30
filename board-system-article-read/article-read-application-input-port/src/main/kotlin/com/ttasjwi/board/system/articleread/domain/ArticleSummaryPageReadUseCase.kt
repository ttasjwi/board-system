package com.ttasjwi.board.system.articleread.domain

import java.time.ZonedDateTime

interface ArticleSummaryPageReadUseCase {
    fun readAllPage(request: ArticleSummaryPageReadRequest): ArticleSummaryPageReadResponse
}

data class ArticleSummaryPageReadRequest(
    val boardId: Long,
    val page: Long?,
    val pageSize: Long?,
)

data class ArticleSummaryPageReadResponse(
    val page: Long,
    val pageSize: Long,
    val pageGroupSize: Long,
    val pageGroupStart: Long,
    val pageGroupEnd: Long,
    val hasNextPage: Boolean,
    val hasNextPageGroup: Boolean,
    val articles: List<Article>,
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
