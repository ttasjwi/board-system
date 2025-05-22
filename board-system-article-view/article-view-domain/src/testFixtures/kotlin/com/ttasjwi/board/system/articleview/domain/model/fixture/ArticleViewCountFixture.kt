package com.ttasjwi.board.system.articleview.domain.model.fixture

import com.ttasjwi.board.system.articleview.domain.model.ArticleViewCount

fun articleViewCountFixture(
    articleId: Long = 1L,
    viewCount: Long = 0L
): ArticleViewCount {
   return ArticleViewCount(
       articleId = articleId,
       viewCount = viewCount
   )
}
