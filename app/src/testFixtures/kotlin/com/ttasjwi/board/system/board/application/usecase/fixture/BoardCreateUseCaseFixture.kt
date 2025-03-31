package com.ttasjwi.board.system.board.application.usecase.fixture

import com.ttasjwi.board.system.board.application.usecase.BoardCreateRequest
import com.ttasjwi.board.system.board.application.usecase.BoardCreateResponse
import com.ttasjwi.board.system.board.application.usecase.BoardCreateUseCase
import com.ttasjwi.board.system.common.time.fixture.timeFixture

class BoardCreateUseCaseFixture : BoardCreateUseCase {

    override fun createBoard(request: BoardCreateRequest): BoardCreateResponse {
        return BoardCreateResponse(
            boardId = "1",
            name = request.name!!,
            description = request.description!!,
            managerId = "1557",
            slug = request.slug!!,
            createdAt = timeFixture(minute = 6)
        )
    }
}
