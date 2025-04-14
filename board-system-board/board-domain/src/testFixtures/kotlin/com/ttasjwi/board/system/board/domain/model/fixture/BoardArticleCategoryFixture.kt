package com.ttasjwi.board.system.board.domain.model.fixture

import com.ttasjwi.board.system.board.domain.model.BoardArticleCategory
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

fun boardArticleCategoryFixture(
    boardArticleCategoryId: Long = 12345L,
    name: String = "테스트",
    slug: String = "testslug",
    boardId: Long = 12455657L,
    allowSelfDelete: Boolean = true,
    allowLike: Boolean = true,
    allowDislike: Boolean = true,
    createdAt: AppDateTime = appDateTimeFixture()
): BoardArticleCategory {
    return BoardArticleCategory(
        boardArticleCategoryId = boardArticleCategoryId,
        name = name,
        slug = slug,
        boardId = boardId,
        allowSelfDelete = allowSelfDelete,
        allowLike = allowLike,
        allowDislike = allowDislike,
        createdAt = createdAt,
    )
}
