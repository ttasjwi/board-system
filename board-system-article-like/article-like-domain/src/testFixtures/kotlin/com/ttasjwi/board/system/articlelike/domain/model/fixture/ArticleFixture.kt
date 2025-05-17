package com.ttasjwi.board.system.articlelike.domain.model.fixture

import com.ttasjwi.board.system.articlelike.domain.model.Article

fun articleFixture(
    articleId: Long = 1L,
    articleCategoryId: Long = 1L,
): Article {
    return Article(
        articleId = articleId,
        articleCategoryId = articleCategoryId,
    )
}
