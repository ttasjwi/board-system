package com.ttasjwi.board.system.articleread.infra.persistence.dto

import com.querydsl.core.annotations.QueryProjection
import com.ttasjwi.board.system.articleread.domain.model.ArticleDetail
import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

class QueryDslArticleDetail
@QueryProjection constructor(
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
    createdAt: LocalDateTime,
    modifiedAt: LocalDateTime
) : ArticleDetail {

    override val createdAt: AppDateTime = AppDateTime.from(createdAt)
    override val modifiedAt: AppDateTime = AppDateTime.from(modifiedAt)

    data class ArticleCategory @QueryProjection constructor(
        override val articleCategoryId: Long,
        override val name: String,
        override val slug: String,
        override val allowSelfDelete: Boolean,
        override val allowLike: Boolean,
        override val allowDislike: Boolean
    ): ArticleDetail.ArticleCategory

    data class Board @QueryProjection constructor(
        override val boardId: Long,
        override val name: String,
        override val slug: String
    ): ArticleDetail.Board

    data class Writer @QueryProjection constructor(
        override val writerId: Long,
        override val nickname: String
    ): ArticleDetail.Writer

    override fun toString(): String {
        return "QueryDslArticleDetail(articleId=$articleId, title='$title', content='$content', articleCategory=$articleCategory, board=$board, writer=$writer, commentCount=$commentCount, liked=$liked, likeCount=$likeCount, disliked=$disliked, dislikeCount=$dislikeCount, createdAt=$createdAt, modifiedAt=$modifiedAt)"
    }
}
