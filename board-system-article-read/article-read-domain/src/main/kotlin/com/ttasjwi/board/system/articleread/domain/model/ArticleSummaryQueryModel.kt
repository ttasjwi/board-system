package com.ttasjwi.board.system.articleread.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime

interface ArticleSummaryQueryModel {
    val articleId: Long
    val title: String
    val board: Board
    val articleCategory: ArticleCategory
    val writer: Writer
    val commentCount: Long
    val likeCount: Long
    val dislikeCount: Long
    val createdAt: AppDateTime

    interface Board {
        val boardId: Long
        val name: String
        val slug: String
    }

    interface ArticleCategory {
        val articleCategoryId: Long
        val name: String
        val slug: String
    }

    interface Writer {
        val writerId: Long
        val nickname: String // 작성시점 닉네임
    }
}
