package com.ttasjwi.board.system.articleread.infra.persistence.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "ArticleReadJpaArticleDislike")
@Table(name = "article_dislikes")
class JpaArticleDislike(
    @Id
    @Column(name = "article_dislike_id")
    val articleDislikeId: Long,

    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "created_at")
    val createdAt: LocalDateTime
)
