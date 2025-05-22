package com.ttasjwi.board.system.articlecomment.infra.persistence.jpa

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentCount
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "ArticleCommentJpaArticleCommentCount")
@Table(name = "article_comment_counts")
class JpaArticleCommentCount(

    @Id
    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "comment_count")
    val commentCount: Long
) {

    fun restoreDomain(): ArticleCommentCount {
        return ArticleCommentCount.restore(
            articleId = articleId,
            commentCount = commentCount
        )
    }
}
