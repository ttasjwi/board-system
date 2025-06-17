package com.ttasjwi.board.system.articleread.infra.persistence.dto

import com.querydsl.core.annotations.QueryProjection
import com.ttasjwi.board.system.articleread.domain.model.ArticleSummaryQueryModel
import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

class QueryDslArticleSummaryQueryModel @QueryProjection constructor(
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
    override val createdAt: LocalDateTime,
) : ArticleSummaryQueryModel {

    override fun toString(): String {
        return "QueryDslArticleSummaryQueryModel(articleId=$articleId, title='$title', boardId=$boardId, boardName='$boardName', boardSlug='$boardSlug', articleCategoryId=$articleCategoryId, articleCategoryName='$articleCategoryName', articleCategorySlug='$articleCategorySlug', writerId=$writerId, writerNickname='$writerNickname', commentCount=$commentCount, likeCount=$likeCount, dislikeCount=$dislikeCount, createdAt=${AppDateTime.from(createdAt)})"
    }
}
