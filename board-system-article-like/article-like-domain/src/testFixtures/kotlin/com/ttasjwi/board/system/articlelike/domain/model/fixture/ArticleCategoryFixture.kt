package com.ttasjwi.board.system.articlelike.domain.model.fixture

import com.ttasjwi.board.system.articlelike.domain.model.ArticleCategory

fun articleCategoryFixture(
    articleCategoryId: Long = 1L,
    allowLike: Boolean = true,
    allowDislike: Boolean = true,
): ArticleCategory {
    return ArticleCategory(
        articleCategoryId = articleCategoryId,
        allowLike = allowLike,
        allowDislike = allowDislike,
    )
}
