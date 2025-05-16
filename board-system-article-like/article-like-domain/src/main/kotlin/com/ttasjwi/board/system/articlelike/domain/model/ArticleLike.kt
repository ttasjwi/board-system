package com.ttasjwi.board.system.articlelike.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

class ArticleLike
internal constructor(
    val articleLikeId: Long,
    val articleId: Long,
    val userId: Long,
    val createdAt: AppDateTime,
) {

    companion object {
        fun create(
            articleLikeId: Long,
            articleId: Long,
            userId: Long,
            currentTime: AppDateTime,
        ): ArticleLike {
            return ArticleLike(
                articleLikeId = articleLikeId,
                articleId = articleId,
                userId = userId,
                createdAt = currentTime,
            )
        }

        fun restore(
            articleLikeId: Long,
            articleId: Long,
            userId: Long,
            createdAt: LocalDateTime,
        ): ArticleLike {
            return ArticleLike(
                articleLikeId = articleLikeId,
                articleId = articleId,
                userId = userId,
                createdAt = AppDateTime.from(createdAt),
            )
        }
    }

    override fun toString(): String {
        return "ArticleLike(articleLikeId=$articleLikeId, articleId=$articleId, userId=$userId, createdAt=$createdAt)"
    }
}
