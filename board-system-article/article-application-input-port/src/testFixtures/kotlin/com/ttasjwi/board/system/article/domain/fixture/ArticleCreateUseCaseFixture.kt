package com.ttasjwi.board.system.article.domain.fixture

import com.ttasjwi.board.system.article.domain.ArticleCreateRequest
import com.ttasjwi.board.system.article.domain.ArticleCreateResponse
import com.ttasjwi.board.system.article.domain.ArticleCreateUseCase
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

class ArticleCreateUseCaseFixture : ArticleCreateUseCase {

    override fun create(request: ArticleCreateRequest): ArticleCreateResponse {
        return ArticleCreateResponse(
            articleId = "1",
            title = request.title!!,
            content = request.content!!,
            boardId = request.boardId!!.toString(),
            articleCategoryId = request.articleCategoryId!!.toString(),
            writerId = "155557",
            createdAt = appDateTimeFixture(minute = 5).toZonedDateTime(),
            modifiedAt = appDateTimeFixture(minute = 5).toZonedDateTime()
        )
    }
}
