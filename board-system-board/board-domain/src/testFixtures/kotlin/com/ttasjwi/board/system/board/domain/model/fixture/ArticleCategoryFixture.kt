package com.ttasjwi.board.system.board.domain.model.fixture

import com.ttasjwi.board.system.board.domain.model.ArticleCategory
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

fun articleCategoryFixture(
    articleCategoryId: Long = 12345L,
    name: String = "테스트",
    slug: String = "testslug",
    boardId: Long = 12455657L,
    allowWrite: Boolean = true,
    allowSelfEditDelete: Boolean = true,
    allowComment: Boolean = true,
    allowLike: Boolean = true,
    allowDislike: Boolean = true,
    createdAt: AppDateTime = appDateTimeFixture()
): ArticleCategory {
    return ArticleCategory(
        articleCategoryId = articleCategoryId,
        name = name,
        slug = slug,
        boardId = boardId,
        allowWrite = allowWrite,
        allowSelfEditDelete = allowSelfEditDelete,
        allowComment = allowComment,
        allowLike = allowLike,
        allowDislike = allowDislike,
        createdAt = createdAt,
    )
}
