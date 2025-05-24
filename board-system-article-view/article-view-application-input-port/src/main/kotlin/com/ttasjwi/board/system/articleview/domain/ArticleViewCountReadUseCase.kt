package com.ttasjwi.board.system.articleview.domain

interface ArticleViewCountReadUseCase {

    fun readViewCount(articleId: Long): ArticleViewCountReadResponse
}

data class ArticleViewCountReadResponse(
    val articleId: String,
    val viewCount: Long,
)
