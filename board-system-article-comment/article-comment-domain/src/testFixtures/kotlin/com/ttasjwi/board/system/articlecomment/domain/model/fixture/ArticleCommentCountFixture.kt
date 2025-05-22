package com.ttasjwi.board.system.articlecomment.domain.model.fixture

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentCount

fun articleCommentCountFixture(
    articleId: Long = 1L,
    commentCount: Long = 0L,
): ArticleCommentCount {
    return ArticleCommentCount(
        articleId = articleId,
        commentCount = commentCount
    )
}
