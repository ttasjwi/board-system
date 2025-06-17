package com.ttasjwi.board.system.articleread.infra.persistence.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "ArticleReadJpaBoardArticleCount")
@Table(name = "board_article_counts")
class JpaBoardArticleCount(

    @Id
    @Column(name = "board_id")
    val boardId: Long,

    @Column(name = "article_count")
    val articleCount: Long
)
