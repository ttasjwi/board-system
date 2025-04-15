package com.ttasjwi.board.system.board.domain.fixture

import com.ttasjwi.board.system.board.domain.BoardArticleCategoryCreateRequest
import com.ttasjwi.board.system.board.domain.BoardArticleCategoryCreateResponse
import com.ttasjwi.board.system.board.domain.BoardArticleCategoryCreateUseCase
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

class BoardArticleCategoryCreateUseCaseFixture : BoardArticleCategoryCreateUseCase {


    override fun create(boardId: Long, request: BoardArticleCategoryCreateRequest): BoardArticleCategoryCreateResponse {
        return BoardArticleCategoryCreateResponse(
            boardArticleCategoryId = "12435346",
            boardId = boardId.toString(),
            name = request.name!!,
            slug = request.slug!!,
            allowSelfDelete = request.allowSelfDelete!!,
            allowLike = request.allowLike!!,
            allowDislike = request.allowDislike!!,
            createdAt = appDateTimeFixture(dayOfMonth = 1).toZonedDateTime(),
        )
    }
}
