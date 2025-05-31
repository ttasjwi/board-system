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

    @Column(name = "board_id")
    val boardId: Long,

    @Column(name = "name")
    var name: String,

    @Column(name = "slug")
    val slug: String,

    @Column(name = "allow_write")
    var allowWrite: Boolean,

    @Column(name = "allow_self_edit_delete")
    var allowSelfEditDelete: Boolean,

    @Column(name = "allow_comment")
    var allowComment: Boolean,

    @Column(name = "allow_like")
    var allowLike: Boolean,

    @Column(name = "allow_dislike")
    var allowDislike: Boolean,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,
) {

    companion object {

        fun from(articleCategory: ArticleCategory): JpaArticleCategory {
            return JpaArticleCategory(
                articleCategoryId = articleCategory.articleCategoryId,
                boardId = articleCategory.boardId,
                name = articleCategory.name,
                slug = articleCategory.slug,
                allowWrite = articleCategory.allowWrite,
                allowSelfEditDelete = articleCategory.allowSelfEditDelete,
                allowComment = articleCategory.allowComment,
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
            allowWrite = allowWrite,
            allowSelfEditDelete = allowSelfEditDelete,
            allowComment = allowComment,
            allowLike = allowLike,
            allowDislike = allowDislike,
            createdAt = createdAt,
        )
    }
}
