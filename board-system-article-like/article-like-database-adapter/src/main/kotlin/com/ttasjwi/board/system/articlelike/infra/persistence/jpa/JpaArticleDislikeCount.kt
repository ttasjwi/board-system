package com.ttasjwi.board.system.articlelike.infra.persistence.jpa

import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislikeCount
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "article_dislike_counts")
class JpaArticleDislikeCount(
    @Id
    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "dislike_count")
    val dislikeCount: Long,
) {

    companion object {

        fun from(articleDislikeCount: ArticleDislikeCount): JpaArticleDislikeCount {
            return JpaArticleDislikeCount(
                articleId = articleDislikeCount.articleId,
                dislikeCount = articleDislikeCount.dislikeCount
            )
        }
    }

    fun restoreDomain(): ArticleDislikeCount {
        return ArticleDislikeCount.restore(
            articleId = this.articleId,
            dislikeCount = this.dislikeCount
        )
    }
}
