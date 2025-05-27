package com.ttasjwi.board.system.articleread.infra.persistence.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "ArticleReadJpaArticleCommentCount")
@Table(name = "article_comment_counts")
class JpaArticleCommentCount(

    @Id
    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "comment_count")
    val commentCount: Long
)
