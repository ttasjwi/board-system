package com.ttasjwi.board.system.articleread.infra.persistence.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "ArticleReadJpaArticleLike")
@Table(name = "article_likes")
class JpaArticleLike(
    @Id
    @Column(name = "article_like_id")
    val articleLikeId: Long,

    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "created_at")
    val createdAt: LocalDateTime
)
