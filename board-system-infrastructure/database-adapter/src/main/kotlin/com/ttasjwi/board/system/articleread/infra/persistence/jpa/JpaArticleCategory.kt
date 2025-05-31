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

    @Column(name = "board_id", nullable = false)
    val boardId: Long,

    @Column(name = "name", length = 20, nullable = false)
    var name: String,

    @Column(name = "slug", length = 8, nullable = false)
    var slug: String,

    @Column(name = "allow_self_edit_delete", nullable = false)
    var allowSelfEditDelete: Boolean,

    @Column(name = "allow_like", nullable = false)
    var allowLike: Boolean,

    @Column(name = "allow_dislike", nullable = false)
    var allowDislike: Boolean,

    @Column(name = "created_at", columnDefinition = "DATETIME", nullable = false)
    val createdAt: LocalDateTime,
)
