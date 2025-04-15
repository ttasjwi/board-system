package com.ttasjwi.board.system.board.infra.persistence.jpa

import com.ttasjwi.board.system.board.domain.model.BoardArticleCategory
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "board_article_categories")
class JpaBoardArticleCategory(

    @Id
    @Column(name = "board_article_category_id")
    val boardArticleCategoryId: Long,

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

        fun from(boardArticleCategory: BoardArticleCategory): JpaBoardArticleCategory {
            return JpaBoardArticleCategory(
                boardArticleCategoryId = boardArticleCategory.boardArticleCategoryId,
                boardId = boardArticleCategory.boardId,
                name = boardArticleCategory.name,
                slug = boardArticleCategory.slug,
                allowSelfDelete = boardArticleCategory.allowSelfDelete,
                allowLike = boardArticleCategory.allowLike,
                allowDislike = boardArticleCategory.allowDislike,
                createdAt = boardArticleCategory.createdAt.toLocalDateTime(),
            )
        }
    }

    internal fun restoreDomain(): BoardArticleCategory {
        return BoardArticleCategory.restore(
            boardArticleCategoryId = boardArticleCategoryId,
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
