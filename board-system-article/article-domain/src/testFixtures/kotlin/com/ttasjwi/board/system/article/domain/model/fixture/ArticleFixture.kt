package com.ttasjwi.board.system.article.domain.model.fixture

import com.ttasjwi.board.system.article.domain.model.Article
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

fun articleFixture(
    articleId: Long = 1L,
    title: String = "제목",
    content: String = "본문",
    boardId: Long = 1L,
    articleCategoryId: Long = 1L,
    writerId: Long = 1L,
    writerNickname: String = "작성자",
    createdAt: AppDateTime = appDateTimeFixture(),
    modifiedAt: AppDateTime = createdAt,
): Article {
    return Article(
        articleId = articleId,
        title = title,
        content = content,
        boardId = boardId,
        articleCategoryId = articleCategoryId,
        writerId = writerId,
        writerNickname = writerNickname,
        createdAt = createdAt,
        modifiedAt = modifiedAt,
    )
}
