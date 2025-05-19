package com.ttasjwi.board.system.articlelike.domain

interface ArticleLikeCountReadUseCase {

    fun readLikeCount(articleId: Long): ArticleLikeCountReadResponse
}

data class ArticleLikeCountReadResponse(
    val articleId: String,
    val likeCount: Long
)
