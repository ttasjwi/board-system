package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

class BoardArticleCategory(
    val boardArticleCategoryId: Long,
    val boardId: Long,
    name: String,
    val slug: String,
    allowSelfDelete: Boolean,
    allowLike: Boolean,
    allowDislike: Boolean,
    val createdAt: AppDateTime
) {

    var name: String = name
        private set

    var allowSelfDelete: Boolean = allowSelfDelete
        private set

    var allowLike: Boolean = allowLike
        private set

    var allowDislike: Boolean = allowDislike
        private set

    companion object {

        fun create(
            boardArticleCategoryId: Long,
            boardId: Long,
            name: String,
            slug: String,
            allowSelfDelete: Boolean,
            allowLike: Boolean,
            allowDislike: Boolean,
            createdAt: AppDateTime
        ): BoardArticleCategory {
            return BoardArticleCategory(
                boardArticleCategoryId = boardArticleCategoryId,
                boardId = boardId,
                name = name,
                slug = slug,
                allowSelfDelete = allowSelfDelete,
                allowLike = allowLike,
                allowDislike = allowDislike,
                createdAt = createdAt
            )
        }

        fun restore(
            boardArticleCategoryId: Long,
            boardId: Long,
            name: String,
            slug: String,
            allowSelfDelete: Boolean,
            allowLike: Boolean,
            allowDislike: Boolean,
            createdAt: LocalDateTime
        ): BoardArticleCategory {
            return BoardArticleCategory(
                boardArticleCategoryId = boardArticleCategoryId,
                boardId = boardId,
                name = name,
                slug = slug,
                allowSelfDelete = allowSelfDelete,
                allowLike = allowLike,
                allowDislike = allowDislike,
                createdAt = AppDateTime.from(createdAt)
            )
        }
    }

    override fun toString(): String {
        return "BoardArticleCategory(boardArticleCategoryId=$boardArticleCategoryId, boardId=$boardId, slug='$slug', createdAt=$createdAt, name='$name', allowSelfDelete=$allowSelfDelete, allowLike=$allowLike, allowDislike=$allowDislike)"
    }

}
