package com.ttasjwi.board.system.articlelike.domain.model.fixture

import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislikeCount

fun articleDislikeCountFixture(
    articleId: Long = 1L,
    dislikeCount: Long = 0L,
): ArticleDislikeCount {
    return ArticleDislikeCount(
        articleId = articleId,
        dislikeCount = dislikeCount
    )
}
