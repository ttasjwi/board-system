package com.ttasjwi.board.system.articlelike.infra.persistence.jpa

import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislike
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "article_dislikes")
class JpaArticleDislike(
    @Id
    @Column(name = "article_dislike_id")
    val articleDislikeId: Long,

    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "created_at")
    val createdAt: LocalDateTime
) {

    companion object {

        fun from(articleDislike: ArticleDislike): JpaArticleDislike {
            return JpaArticleDislike(
                articleDislikeId = articleDislike.articleDislikeId,
                articleId = articleDislike.articleId,
                userId = articleDislike.userId,
                createdAt = articleDislike.createdAt.toLocalDateTime()
            )
        }
    }

    fun restoreDomain(): ArticleDislike {
        return ArticleDislike.restore(
            articleDislikeId = articleDislikeId,
            articleId = articleId,
            userId = userId,
            createdAt = createdAt
        )
    }
}
