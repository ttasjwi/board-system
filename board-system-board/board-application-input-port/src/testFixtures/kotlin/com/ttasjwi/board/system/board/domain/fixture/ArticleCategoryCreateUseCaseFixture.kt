package com.ttasjwi.board.system.board.domain.fixture

import com.ttasjwi.board.system.board.domain.ArticleCategoryCreateRequest
import com.ttasjwi.board.system.board.domain.ArticleCategoryCreateResponse
import com.ttasjwi.board.system.board.domain.ArticleCategoryCreateUseCase
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

class ArticleCategoryCreateUseCaseFixture : ArticleCategoryCreateUseCase {


    override fun create(boardId: Long, request: ArticleCategoryCreateRequest): ArticleCategoryCreateResponse {
        return ArticleCategoryCreateResponse(
            articleCategoryId = "12435346",
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
