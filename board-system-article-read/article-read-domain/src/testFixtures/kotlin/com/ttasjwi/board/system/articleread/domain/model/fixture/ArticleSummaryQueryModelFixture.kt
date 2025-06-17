package com.ttasjwi.board.system.articleread.domain.model.fixture

import com.ttasjwi.board.system.articleread.domain.model.ArticleSummaryQueryModel
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

fun articleSummaryQueryModelFixture(
    articleId: Long = 1L,
    title: String = "제목",
    articleCategoryId: Long = 2L,
    articleCategoryName: String = "일반",
    articleCategorySlug: String = "general",
    boardId: Long = 3L,
    boardName: String = "테스트",
    boardSlug: String = "test",
    writerId: Long = 4L,
    writerNickname: String = "테스트사용자",
    commentCount: Long = 0L,
    likeCount: Long = 0L,
    dislikeCount: Long = 0L,
    createdAt: AppDateTime = appDateTimeFixture(minute = 0),
): ArticleSummaryQueryModel {
    return TestArticleSummary(
        articleId = articleId,
        title = title,
        articleCategoryId = articleCategoryId,
        articleCategoryName = articleCategoryName,
        articleCategorySlug = articleCategorySlug,
        boardId = boardId,
        boardName = boardName,
        boardSlug = boardSlug,
        writerId = writerId,
        writerNickname = writerNickname,
        commentCount = commentCount,
        likeCount = likeCount,
        dislikeCount = dislikeCount,
        createdAt = createdAt,
    )
}

data class TestArticleSummary(
    override val articleId: Long,
    override val title: String,
    override val boardId: Long,
    override val boardName: String,
    override val boardSlug: String,
    override val articleCategoryId: Long,
    override val articleCategoryName: String,
    override val articleCategorySlug: String,
    override val writerId: Long,
    override val writerNickname: String,
    override val commentCount: Long,
    override val likeCount: Long,
    override val dislikeCount: Long,
    override val createdAt: AppDateTime
): ArticleSummaryQueryModel
