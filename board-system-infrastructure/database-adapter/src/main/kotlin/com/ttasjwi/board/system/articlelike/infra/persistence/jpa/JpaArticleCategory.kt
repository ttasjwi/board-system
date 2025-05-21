package com.ttasjwi.board.system.articlelike.infra.persistence.jpa

import com.ttasjwi.board.system.articlelike.domain.model.ArticleCategory
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "ArticleLikeJpaArticleCategory")
@Table(name = "article_categories")
class JpaArticleCategory(

    @Id
    @Column(name = "article_category_id")
    val articleCategoryId: Long,

    @Column(name = "board_id")
    val boardId: Long,

    @Column(name = "name")
    val name: String,

    @Column(name = "slug")
    val slug: String,

    @Column(name = "allow_self_delete")
    val allowSelfDelete: Boolean,

    @Column(name = "allow_like")
    val allowLike: Boolean,

    @Column(name = "allow_dislike")
    val allowDislike: Boolean,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,
) {

    internal fun restoreDomain(): ArticleCategory {
        return ArticleCategory.restore(
            articleCategoryId = articleCategoryId,
            allowLike = allowLike,
            allowDislike = allowDislike,
        )
    }
}
