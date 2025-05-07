package com.ttasjwi.board.system.board.infra.persistence.jpa

import com.ttasjwi.board.system.board.domain.model.ArticleCategory
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "BoardJpaArticleCategory")
@Table(name = "article_categories")
class JpaArticleCategory(

    @Id
    @Column(name = "article_category_id")
    val articleCategoryId: Long,

    @Column(name = "board_id", nullable = false)
    val boardId: Long,

    @Column(name = "name", length = 20, nullable = false)
    var name: String,

    @Column(name = "slug", length = 8, nullable = false)
    var slug: String,

    @Column(name = "allow_self_delete", nullable = false)
    var allowSelfDelete: Boolean,

    @Column(name = "allow_like", nullable = false)
    var allowLike: Boolean,

    @Column(name = "allow_dislike", nullable = false)
    var allowDislike: Boolean,

    @Column(name = "created_at", columnDefinition = "DATETIME", nullable = false)
    val createdAt: LocalDateTime,
) {

    companion object {

        fun from(articleCategory: ArticleCategory): JpaArticleCategory {
            return JpaArticleCategory(
                articleCategoryId = articleCategory.articleCategoryId,
                boardId = articleCategory.boardId,
                name = articleCategory.name,
                slug = articleCategory.slug,
                allowSelfDelete = articleCategory.allowSelfDelete,
                allowLike = articleCategory.allowLike,
                allowDislike = articleCategory.allowDislike,
                createdAt = articleCategory.createdAt.toLocalDateTime(),
            )
        }
    }

    internal fun restoreDomain(): ArticleCategory {
        return ArticleCategory.restore(
            articleCategoryId = articleCategoryId,
            boardId = boardId,
            name = name,
            slug = slug,
            allowSelfDelete = allowSelfDelete,
            allowLike = allowLike,
            allowDislike = allowDislike,
            createdAt = createdAt,
        )
    }
}
