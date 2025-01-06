package com.ttasjwi.board.system.board.application.usecase

import java.time.ZonedDateTime

interface BoardCreateUseCase {
    fun createBoard(request: BoardCreateRequest): BoardCreateResult
}

data class BoardCreateRequest(
    val name: String?,
    val description: String?,
    val slug: String?,
)

data class BoardCreateResult(
    val createdBoard: CreatedBoard
) {

    data class CreatedBoard(
        val boardId: Long,
        val name: String,
        val description: String,
        val managerId: Long,
        val slug: String,
        val createdAt: ZonedDateTime,
    )
}
