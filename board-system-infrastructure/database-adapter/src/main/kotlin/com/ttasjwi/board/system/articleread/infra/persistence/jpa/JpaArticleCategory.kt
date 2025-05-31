package com.ttasjwi.board.system.articleread.infra.persistence.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "ArticleReadJpaArticleCategory")
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

    @Column(name = "allow_write")
    val allowWrite: Boolean,

    @Column(name = "allow_self_edit_delete")
    val allowSelfEditDelete: Boolean,

    @Column(name = "allow_comment")
    val allowComment: Boolean,

    @Column(name = "allow_like")
    val allowLike: Boolean,

    @Column(name = "allow_dislike")
    val allowDislike: Boolean,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,
)
