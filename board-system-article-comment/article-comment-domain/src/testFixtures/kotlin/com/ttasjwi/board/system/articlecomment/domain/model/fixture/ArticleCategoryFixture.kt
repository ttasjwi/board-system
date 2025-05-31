package com.ttasjwi.board.system.articlecomment.domain.model.fixture

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCategory

fun articleCategoryFixture(
    articleCategoryId: Long = 1L,
    allowComment: Boolean = true,
): ArticleCategory {
    return ArticleCategory(
        articleCategoryId = articleCategoryId,
        allowComment = allowComment
    )
}
