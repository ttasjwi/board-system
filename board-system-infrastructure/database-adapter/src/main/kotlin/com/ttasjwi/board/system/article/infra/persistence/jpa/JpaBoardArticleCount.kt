package com.ttasjwi.board.system.article.infra.persistence.jpa

import com.ttasjwi.board.system.article.domain.model.BoardArticleCount
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "ArticleJpaBoardArticleCount")
@Table(name = "board_article_counts")
class JpaBoardArticleCount(

    @Id
    @Column(name = "board_id")
    val boardId: Long,

    @Column(name = "article_count")
    val articleCount: Long
) {

    fun restoreDomain(): BoardArticleCount {
        return BoardArticleCount.restore(
            boardId = boardId,
            articleCount = articleCount
        )
    }
}
