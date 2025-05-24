package com.ttasjwi.board.system.articlecomment.domain

interface ArticleCommentCountReadUseCase {

    fun readCommentCount(articleId: Long): ArticleCommentCountReadResponse
}

data class ArticleCommentCountReadResponse(
    val articleId: String,
    val commentCount: Long,
)
