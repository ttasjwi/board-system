package com.ttasjwi.board.system.article.domain.fixture

import com.ttasjwi.board.system.article.domain.ArticleReadResponse
import com.ttasjwi.board.system.article.domain.ArticleReadUseCase
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

class ArticleReadUseCaseFixture : ArticleReadUseCase {

    override fun read(articleId: Long): ArticleReadResponse {
        return ArticleReadResponse(
            articleId = articleId.toString(),
            title = "제목",
            content = "본문",
            boardId = "131433451451",
            articleCategoryId = "87364535667",
            writerId = "155557",
            writerNickname = "땃쥐",
            createdAt = appDateTimeFixture(minute = 5).toZonedDateTime(),
            modifiedAt = appDateTimeFixture(minute = 5).toZonedDateTime()
        )
    }
}
