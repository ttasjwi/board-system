package com.ttasjwi.board.system.articlelike.infra.persistence.jpa

import com.ttasjwi.board.system.articlelike.domain.model.ArticleLikeCount
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "article_like_counts")
class JpaArticleLikeCount(
    @Id
    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "like_count")
    val likeCount: Long,
) {

    companion object {

        fun from(articleLikeCount: ArticleLikeCount): JpaArticleLikeCount {
            return JpaArticleLikeCount(
                articleId = articleLikeCount.articleId,
                likeCount = articleLikeCount.likeCount
            )
        }
    }

    fun restoreDomain(): ArticleLikeCount {
        return ArticleLikeCount.restore(
            articleId = this.articleId,
            likeCount = this.likeCount
        )
    }
}
