package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

class ArticleCategory(
    val articleCategoryId: Long,
    val boardId: Long,
    name: String,
    val slug: String,
    allowWrite: Boolean,
    allowSelfEditDelete: Boolean,
    allowComment: Boolean,
    allowLike: Boolean,
    allowDislike: Boolean,
    val createdAt: AppDateTime
) {

    var name: String = name
        private set

    var allowWrite: Boolean = allowWrite
        private set

    var allowSelfEditDelete: Boolean = allowSelfEditDelete
        private set

    var allowComment: Boolean = allowComment
        private set

    var allowLike: Boolean = allowLike
        private set

    var allowDislike: Boolean = allowDislike
        private set

    companion object {

        fun create(
            articleCategoryId: Long,
            boardId: Long,
            name: String,
            slug: String,
            allowWrite: Boolean,
            allowSelfEditDelete: Boolean,
            allowComment: Boolean,
            allowLike: Boolean,
            allowDislike: Boolean,
            createdAt: AppDateTime
        ): ArticleCategory {
            return ArticleCategory(
                articleCategoryId = articleCategoryId,
                boardId = boardId,
                name = name,
                slug = slug,
                allowWrite = allowWrite,
                allowSelfEditDelete = allowSelfEditDelete,
                allowComment = allowComment,
                allowLike = allowLike,
                allowDislike = allowDislike,
                createdAt = createdAt
            )
        }

        fun restore(
            articleCategoryId: Long,
            boardId: Long,
            name: String,
            slug: String,
            allowWrite: Boolean,
            allowSelfEditDelete: Boolean,
            allowComment: Boolean,
            allowLike: Boolean,
            allowDislike: Boolean,
            createdAt: LocalDateTime
        ): ArticleCategory {
            return ArticleCategory(
                articleCategoryId = articleCategoryId,
                boardId = boardId,
                name = name,
                slug = slug,
                allowWrite = allowWrite,
                allowSelfEditDelete = allowSelfEditDelete,
                allowComment = allowComment,
                allowLike = allowLike,
                allowDislike = allowDislike,
                createdAt = AppDateTime.from(createdAt)
            )
        }
    }

    override fun toString(): String {
        return "ArticleCategory(articleCategoryId=$articleCategoryId, name='$name', slug='$slug', boardId=$boardId, allowWrite=$allowWrite, allowSelfEditDelete=$allowSelfEditDelete, allowComment=$allowComment, allowLike=$allowLike, allowDislike=$allowDislike, createdAt=$createdAt)"
    }
}
