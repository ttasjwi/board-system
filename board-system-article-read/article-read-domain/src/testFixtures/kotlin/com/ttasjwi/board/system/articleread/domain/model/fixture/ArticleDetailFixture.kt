package com.ttasjwi.board.system.articleread.domain.model.fixture

import com.ttasjwi.board.system.articleread.domain.model.ArticleDetail
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

fun articleDetailFixture(
    articleId: Long = 1L,
    title: String = "제목",
    content: String = "본문",
    articleCategoryId: Long = 2L,
    articleCategoryName: String = "일반",
    articleCategorySlug: String = "general",
    articleCategoryAllowSelfEditDelete: Boolean = true,
    articleCategoryAllowLike: Boolean = true,
    articleCategoryAllowDislike: Boolean = true,
    boardId: Long = 3L,
    boardName: String = "테스트",
    boardSlug: String = "test",
    writerId: Long = 4L,
    writerNickname: String = "테스트사용자",
    commentCount: Long = 0L,
    liked: Boolean = false,
    likeCount: Long = 0L,
    disliked: Boolean = false,
    dislikeCount: Long = 0L,
    createdAt: AppDateTime = appDateTimeFixture(minute = 0),
    modifiedAt: AppDateTime = appDateTimeFixture(minute = 0)
): ArticleDetail {
    return TestArticleDetail(
        articleId = articleId,
        title = title,
        content = content,
        articleCategory = TestArticleDetail.ArticleCategory(
            articleCategoryId = articleCategoryId,
            name = articleCategoryName,
            slug = articleCategorySlug,
            allowSelfEditDelete = articleCategoryAllowSelfEditDelete,
            allowLike = articleCategoryAllowLike,
            allowDislike = articleCategoryAllowDislike
        ),
        board = TestArticleDetail.Board(
            boardId = boardId,
            name = boardName,
            slug = boardSlug,
        ),
        writer = TestArticleDetail.Writer(
            writerId = writerId,
            nickname = writerNickname,
        ),
        commentCount = commentCount,
        liked = liked,
        likeCount = likeCount,
        disliked = disliked,
        dislikeCount = dislikeCount,
        createdAt = createdAt,
        modifiedAt = modifiedAt,
    )
}

internal data class TestArticleDetail
internal constructor(
    override val articleId: Long,
    override val title: String,
    override val content: String,
    override val articleCategory: ArticleDetail.ArticleCategory,
    override val board: ArticleDetail.Board,
    override val writer: ArticleDetail.Writer,
    override val commentCount: Long,
    override val liked: Boolean,
    override val likeCount: Long,
    override val disliked: Boolean,
    override val dislikeCount: Long,
    override val createdAt: AppDateTime,
    override val modifiedAt: AppDateTime
) : ArticleDetail {

    internal data class ArticleCategory(
        override val articleCategoryId: Long,
        override val name: String,
        override val slug: String,
        override val allowSelfEditDelete: Boolean,
        override val allowLike: Boolean,
        override val allowDislike: Boolean
    ) : ArticleDetail.ArticleCategory

    internal data class Board(
        override val boardId: Long,
        override val name: String,
        override val slug: String
    ) : ArticleDetail.Board

    internal data class Writer(
        override val writerId: Long,
        override val nickname: String
    ) : ArticleDetail.Writer
}
