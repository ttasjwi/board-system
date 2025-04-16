package com.ttasjwi.board.system.board.domain

import java.time.ZonedDateTime

interface ArticleCategoryCreateUseCase {
    fun create(boardId: Long, request: ArticleCategoryCreateRequest): ArticleCategoryCreateResponse
}

data class ArticleCategoryCreateRequest(
    val name: String?,
    val slug: String?,
    val allowSelfDelete: Boolean?,
    val allowLike: Boolean?,
    val allowDislike: Boolean?
)

data class ArticleCategoryCreateResponse(
    val articleCategoryId: String,
    val boardId: String,
    val name: String,
    val slug: String,
    val allowSelfDelete: Boolean,
    val allowLike: Boolean,
    val allowDislike: Boolean,
    val createdAt: ZonedDateTime
)
