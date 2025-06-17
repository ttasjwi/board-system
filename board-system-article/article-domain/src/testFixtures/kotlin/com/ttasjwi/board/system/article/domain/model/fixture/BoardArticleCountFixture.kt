package com.ttasjwi.board.system.article.domain.model.fixture

import com.ttasjwi.board.system.article.domain.model.BoardArticleCount

fun boardArticleCountFixture(
    boardId: Long = 1L,
    articleCount: Long = 0L,
): BoardArticleCount {
    return BoardArticleCount(
        boardId = boardId,
        articleCount = articleCount
    )
}
