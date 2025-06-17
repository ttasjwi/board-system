package com.ttasjwi.board.system.articleread.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime

interface ArticleSummaryQueryModel {
    val articleId: Long
    val title: String
    val boardId: Long
    val boardName: String
    val boardSlug: String
    val articleCategoryId: Long
    val articleCategoryName: String
    val articleCategorySlug: String
    val writerId: Long
    val writerNickname: String
    val commentCount: Long
    val likeCount: Long
    val dislikeCount: Long
    val createdAt: AppDateTime
}
