package com.ttasjwi.board.system.articlelike.infra.persistence.jpa

import com.ttasjwi.board.system.articlelike.domain.model.ArticleLike
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "article_likes")
class JpaArticleLike (
    @Id
    @Column(name = "article_like_id")
    val articleLikeId: Long,

    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "created_at")
    val createdAt: LocalDateTime
) {

    companion object {

        fun from(articleLike: ArticleLike): JpaArticleLike {
            return JpaArticleLike(
                articleLikeId = articleLike.articleLikeId,
                articleId = articleLike.articleId,
                userId = articleLike.userId,
                createdAt = articleLike.createdAt.toLocalDateTime()
            )
        }
    }

    fun restoreDomain(): ArticleLike {
        return ArticleLike.restore(
            articleLikeId = articleLikeId,
            articleId = articleId,
            userId = userId,
            createdAt = createdAt
        )
    }
}
