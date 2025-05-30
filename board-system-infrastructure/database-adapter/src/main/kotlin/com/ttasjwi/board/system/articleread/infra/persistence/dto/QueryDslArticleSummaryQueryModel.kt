package com.ttasjwi.board.system.articleread.infra.persistence.dto

import com.querydsl.core.annotations.QueryProjection
import com.ttasjwi.board.system.articleread.domain.model.ArticleSummaryQueryModel
import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

class QueryDslArticleSummaryQueryModel @QueryProjection constructor(
    override val articleId: Long,
    override val title: String,
    override val board: ArticleSummaryQueryModel.Board,
    override val articleCategory: ArticleSummaryQueryModel.ArticleCategory,
    override val writer: ArticleSummaryQueryModel.Writer,
    override val commentCount: Long,
    override val likeCount: Long,
    override val dislikeCount: Long,
    createdAt: LocalDateTime,
) : ArticleSummaryQueryModel {

    override val createdAt: AppDateTime = AppDateTime.from(createdAt)


    data class Board @QueryProjection constructor(
        override val boardId: Long,
        override val name: String,
        override val slug: String
    ): ArticleSummaryQueryModel.Board

    data class ArticleCategory @QueryProjection constructor(
        override val articleCategoryId: Long,
        override val name: String,
        override val slug: String
    ): ArticleSummaryQueryModel.ArticleCategory

    data class Writer @QueryProjection constructor(
        override val writerId: Long,
        override val nickname: String
    ): ArticleSummaryQueryModel.Writer

    override fun toString(): String {
        return "QueryDslArticleSummaryQueryModel(articleId=$articleId, title='$title', board=$board, articleCategory=$articleCategory, writer=$writer, commentCount=$commentCount, likeCount=$likeCount, dislikeCount=$dislikeCount, createdAt=$createdAt)"
    }


}
