package com.ttasjwi.board.system.articlelike.domain.model.fixture

import com.ttasjwi.board.system.articlelike.domain.model.ArticleLikeCount

fun articleLikeCountFixture(
    articleId: Long = 1L,
    likeCount: Long = 0L,
): ArticleLikeCount {
    return ArticleLikeCount(
        articleId = articleId,
        likeCount = likeCount
    )
}
