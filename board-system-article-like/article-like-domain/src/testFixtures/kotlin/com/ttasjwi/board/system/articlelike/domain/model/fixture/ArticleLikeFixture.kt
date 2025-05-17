package com.ttasjwi.board.system.articlelike.domain.model.fixture

import com.ttasjwi.board.system.articlelike.domain.model.ArticleLike
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

fun articleLikeFixture(
    articleLikeId: Long = 1L,
    articleId: Long = 1L,
    userId: Long = 1L,
    createdAt: AppDateTime = appDateTimeFixture()
): ArticleLike {
    return ArticleLike(
        articleId = articleId,
        userId = userId,
        createdAt = createdAt,
        articleLikeId = articleLikeId,
    )
}
