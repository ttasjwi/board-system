package com.ttasjwi.board.system.articlelike.domain.model.fixture

import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislike
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

fun articleDislikeFixture(
    articleDislikeId: Long = 1L,
    articleId: Long = 1L,
    userId: Long = 1L,
    createdAt: AppDateTime = appDateTimeFixture()
): ArticleDislike {
    return ArticleDislike(
        articleDislikeId = articleDislikeId,
        articleId = articleId,
        userId = userId,
        createdAt = createdAt,
    )
}
