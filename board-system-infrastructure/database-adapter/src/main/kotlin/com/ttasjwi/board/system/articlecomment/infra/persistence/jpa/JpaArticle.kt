package com.ttasjwi.board.system.articlecomment.infra.persistence.jpa

import com.ttasjwi.board.system.articlecomment.domain.model.Article
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "ArticleCommentJpaArticle")
@Table(name = "articles")
class JpaArticle(
    @Id
    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "board_id")
    val boardId: Long,

    @Column(name = "article_category_id")
    var articleCategoryId: Long,

    @Column(name = "writer_id")
    val writerId: Long,

    @Column(name = "writer_nickname")
    val writerNickname: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "modified_at")
    var modifiedAt: LocalDateTime,
) {

    fun restoreDomain(): Article {
        return Article(
            articleId = articleId,
            writerId = writerId,
            articleCategoryId = articleCategoryId,
        )
    }
}
