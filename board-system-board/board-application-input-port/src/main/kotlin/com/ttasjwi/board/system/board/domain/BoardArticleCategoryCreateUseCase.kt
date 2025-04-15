package com.ttasjwi.board.system.board.domain

import java.time.ZonedDateTime

interface BoardArticleCategoryCreateUseCase {
    fun create(boardId: Long, request: BoardArticleCategoryCreateRequest): BoardArticleCategoryCreateResponse
}

data class BoardArticleCategoryCreateRequest(
    val name: String?,
    val slug: String?,
    val allowSelfDelete: Boolean?,
    val allowLike: Boolean?,
    val allowDislike: Boolean?
)

data class BoardArticleCategoryCreateResponse(
    val boardArticleCategoryId: String,
    val boardId: String,
    val name: String,
    val slug: String,
    val allowSelfDelete: Boolean,
    val allowLike: Boolean,
    val allowDislike: Boolean,
    val createdAt: ZonedDateTime
)
