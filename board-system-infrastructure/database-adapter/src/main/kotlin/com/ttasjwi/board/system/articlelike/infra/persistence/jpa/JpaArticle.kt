package com.ttasjwi.board.system.articlelike.infra.persistence.jpa

import com.ttasjwi.board.system.articlelike.domain.model.Article
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "ArticleLikeJpaArticle")
@Table(name = "articles")
class JpaArticle(

    @Id
    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "title")
    val title: String,

    @Column(name = "content")
    val content: String,

    @Column(name = "board_id")
    val boardId: Long,

    @Column(name = "article_category_id")
    val articleCategoryId: Long,

    @Column(name = "writer_id")
    val writerId: Long,

    @Column(name = "writer_nickname")
    val writerNickname: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "modified_at")
    val modifiedAt: LocalDateTime,
) {

    fun retoreToDomain(): Article {
        return Article.restore(
            articleId = this.articleId,
            articleCategoryId = this.articleCategoryId,
        )
    }
}
