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
        articleCategory = TestArticleSummary.ArticleCategory(
            articleCategoryId = articleCategoryId,
            name = articleCategoryName,
            slug = articleCategorySlug,
        ),
        board = TestArticleSummary.Board(
            boardId = boardId,
            name = boardName,
            slug = boardSlug,
        ),
        writer = TestArticleSummary.Writer(
            writerId = writerId,
            nickname = writerNickname,
        ),
        commentCount = commentCount,
        likeCount = likeCount,
        dislikeCount = dislikeCount,
        createdAt = createdAt,
    )
}

data class TestArticleSummary(
    override val articleId: Long,
    override val title: String,
    override val board: ArticleSummaryQueryModel.Board,
    override val articleCategory: ArticleSummaryQueryModel.ArticleCategory,
    override val writer: ArticleSummaryQueryModel.Writer,
    override val commentCount: Long,
    override val likeCount: Long,
    override val dislikeCount: Long,
    override val createdAt: AppDateTime
): ArticleSummaryQueryModel {

    data class Board(
        override val boardId: Long,
        override val name: String,
        override val slug: String
    ) : ArticleSummaryQueryModel.Board

    data class ArticleCategory(
        override val articleCategoryId: Long,
        override val name: String,
        override val slug: String
    ): ArticleSummaryQueryModel.ArticleCategory

    data class Writer(
        override val writerId: Long,
        override val nickname: String
    ): ArticleSummaryQueryModel.Writer
}
