package com.ttasjwi.board.system.articleread.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime

interface ArticleDetail {
    val articleId: Long
    val title: String
    val content: String
    val articleCategory: ArticleCategory
    val board: Board
    val writer: Writer
    val commentCount: Long
    val liked: Boolean
    val likeCount: Long
    val disliked: Boolean
    val dislikeCount: Long
    val createdAt: AppDateTime
    val modifiedAt: AppDateTime

    interface Writer {
        val writerId: Long
        val nickname: String
    }

    interface ArticleCategory {
        val articleCategoryId: Long
        val name: String
        val slug: String
        val allowSelfDelete: Boolean
        val allowLike: Boolean
        val allowDislike: Boolean
    }

    interface Board {
        val boardId: Long
        val name: String
        val slug: String
    }
}
