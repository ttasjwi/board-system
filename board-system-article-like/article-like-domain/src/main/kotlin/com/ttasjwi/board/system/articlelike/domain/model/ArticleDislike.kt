package com.ttasjwi.board.system.articlelike.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

class ArticleDislike
internal constructor(
    val articleDislikeId: Long,
    val articleId: Long,
    val userId: Long,
    val createdAt: AppDateTime,
) {

    companion object {
        fun create(
            articleDislikeId: Long,
            articleId: Long,
            userId: Long,
            currentTime: AppDateTime,
        ): ArticleDislike {
            return ArticleDislike(
                articleDislikeId = articleDislikeId,
                articleId = articleId,
                userId = userId,
                createdAt = currentTime,
            )
        }

        fun restore(
            articleDislikeId: Long,
            articleId: Long,
            userId: Long,
            createdAt: LocalDateTime,
        ): ArticleDislike {
            return ArticleDislike(
                articleDislikeId = articleDislikeId,
                articleId = articleId,
                userId = userId,
                createdAt = AppDateTime.from(createdAt),
            )
        }
    }

    override fun toString(): String {
        return "ArticleDislike(articleDislikeId=$articleDislikeId, articleId=$articleId, userId=$userId, createdAt=$createdAt)"
    }
}
