package com.ttasjwi.board.system.articlecomment.domain.model.fixture

import com.ttasjwi.board.system.articlecomment.domain.model.Article

fun articleFixture(
    articleId: Long = 1L,
    writerId: Long = 145L,
    articleCategoryId: Long = 1L,
): Article {
    return Article(
        articleId = articleId,
        writerId = writerId,
        articleCategoryId = articleCategoryId,
    )
}
