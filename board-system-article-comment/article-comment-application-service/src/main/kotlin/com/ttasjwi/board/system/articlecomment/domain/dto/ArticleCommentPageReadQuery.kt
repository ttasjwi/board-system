package com.ttasjwi.board.system.articlecomment.domain.dto

data class ArticleCommentPageReadQuery(
    val articleId: Long,
    val page: Long,
    val pageSize: Long
)
