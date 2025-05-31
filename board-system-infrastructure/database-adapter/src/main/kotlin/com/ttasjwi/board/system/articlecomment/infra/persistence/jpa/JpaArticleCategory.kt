package com.ttasjwi.board.system.articlecomment.infra.persistence.jpa

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCategory
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "ArticleCommentJpaArticleCategory")
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

    internal fun restoreDomain(): ArticleCategory {
        return ArticleCategory.restore(
            articleCategoryId = articleCategoryId,
            allowComment = allowComment,
        )
    }
}
