package com.ttasjwi.board.system.board.application.usecase.fixture

import com.ttasjwi.board.system.board.application.usecase.BoardCreateRequest
import com.ttasjwi.board.system.board.application.usecase.BoardCreateResult
import com.ttasjwi.board.system.board.application.usecase.BoardCreateUseCase
import com.ttasjwi.board.system.common.time.fixture.timeFixture

class BoardCreateUseCaseFixture : BoardCreateUseCase {

    override fun createBoard(request: BoardCreateRequest): BoardCreateResult {
        return BoardCreateResult(
            createdBoard = BoardCreateResult.CreatedBoard(
                boardId = 1L,
                name = request.name!!,
                description = request.description!!,
                managerId = 1557L,
                slug = request.slug!!,
                createdAt = timeFixture(minute = 6)
            )
        )
    }
}
