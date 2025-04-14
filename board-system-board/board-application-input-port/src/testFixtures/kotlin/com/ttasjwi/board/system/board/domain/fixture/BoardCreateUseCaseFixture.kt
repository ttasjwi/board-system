package com.ttasjwi.board.system.board.domain.fixture

import com.ttasjwi.board.system.board.domain.BoardCreateRequest
import com.ttasjwi.board.system.board.domain.BoardCreateResponse
import com.ttasjwi.board.system.board.domain.BoardCreateUseCase
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

class BoardCreateUseCaseFixture : BoardCreateUseCase {

    override fun createBoard(request: BoardCreateRequest): BoardCreateResponse {
        return BoardCreateResponse(
            boardId = "1",
            name = request.name!!,
            description = request.description!!,
            managerId = "1557",
            slug = request.slug!!,
            createdAt = appDateTimeFixture(minute = 6).toZonedDateTime()
        )
    }
}
