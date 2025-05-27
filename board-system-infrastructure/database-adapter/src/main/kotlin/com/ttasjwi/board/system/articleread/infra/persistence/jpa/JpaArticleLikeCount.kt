package com.ttasjwi.board.system.articleread.infra.persistence.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "ArticleReadJpaArticleLikeCount")
@Table(name = "article_like_counts")
class JpaArticleLikeCount(
    @Id
    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "like_count")
    val likeCount: Long,
)
