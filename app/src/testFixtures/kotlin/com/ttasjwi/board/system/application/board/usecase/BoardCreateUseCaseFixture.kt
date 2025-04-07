package com.ttasjwi.board.system.application.board.usecase

import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture

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
