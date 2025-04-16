package com.ttasjwi.board.system.article.infra.persistence.jpa

import com.ttasjwi.board.system.article.domain.model.Article
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
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

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "modified_at")
    var modifiedAt: LocalDateTime,
) {

    companion object {

        internal fun from(article: Article): JpaArticle {
            return JpaArticle(
                articleId = article.articleId,
                title = article.title,
                content = article.content,
                boardId = article.boardId,
                articleCategoryId = article.articleCategoryId,
                writerId = article.writerId,
                createdAt = article.createdAt.toLocalDateTime(),
                modifiedAt = article.modifiedAt.toLocalDateTime()
            )
        }
    }

    fun retoreToDomain(): Article {
        return Article.restore(
            articleId = this.articleId,
            title = this.title,
            content = this.content,
            boardId = this.boardId,
            articleCategoryId = this.articleCategoryId,
            writerId = this.writerId,
            createdAt = this.createdAt,
            modifiedAt = this.modifiedAt
        )
    }
}
