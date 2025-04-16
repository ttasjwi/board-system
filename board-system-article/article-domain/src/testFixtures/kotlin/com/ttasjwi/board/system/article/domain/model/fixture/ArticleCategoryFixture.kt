package com.ttasjwi.board.system.article.domain.model.fixture

import com.ttasjwi.board.system.article.domain.model.ArticleCategory

fun articleCategoryFixture(
    articleCategoryId: Long = 12345L,
    boardId: Long = 12455657L,
    allowSelfDelete: Boolean = true
): ArticleCategory {
    return ArticleCategory(
        articleCategoryId = articleCategoryId,
        boardId = boardId,
        allowSelfDelete = allowSelfDelete,
    )
}
