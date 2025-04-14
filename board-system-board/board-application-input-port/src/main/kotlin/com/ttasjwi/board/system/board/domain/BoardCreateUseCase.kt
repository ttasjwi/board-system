package com.ttasjwi.board.system.board.domain

import java.time.ZonedDateTime

interface BoardCreateUseCase {
    fun createBoard(request: BoardCreateRequest): BoardCreateResponse
}

data class BoardCreateRequest(
    val name: String?,
    val description: String?,
    val slug: String?,
)

data class BoardCreateResponse(
    val boardId: String,
    val name: String,
    val description: String,
    val managerId: String,
    val slug: String,
    val createdAt: ZonedDateTime,
)
