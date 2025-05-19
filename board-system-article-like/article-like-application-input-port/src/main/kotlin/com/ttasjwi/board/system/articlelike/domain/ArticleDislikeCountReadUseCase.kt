package com.ttasjwi.board.system.articlelike.domain

interface ArticleDislikeCountReadUseCase {
    fun readDislikeCount(articleId: Long): ArticleDislikeCountReadResponse
}

data class ArticleDislikeCountReadResponse(
    val articleId: String,
    val dislikeCount: Long
)
