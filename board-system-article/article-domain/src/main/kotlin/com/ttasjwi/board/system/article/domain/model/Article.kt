package com.ttasjwi.board.system.article.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

class Article
internal constructor(
    val articleId: Long,
    title: String,
    content: String,
    val boardId: Long,
    boardArticleCategoryId: Long,
    val writerId: Long,
    val createdAt: AppDateTime,
    modifiedAt: AppDateTime,
) {

    var title: String = title
        private set

    var content: String = content
        private set

    var boardArticleCategoryId: Long = boardArticleCategoryId
        private set

    var modifiedAt: AppDateTime = modifiedAt
        private set


    companion object {

        fun create(
            articleId: Long,
            title: String,
            content: String,
            boardId: Long,
            boardArticleCategoryId: Long,
            writerId: Long,
            createdAt: AppDateTime,
        ): Article {
            return Article(
                articleId = articleId,
                title = title,
                content = content,
                boardId = boardId,
                boardArticleCategoryId = boardArticleCategoryId,
                writerId = writerId,
                createdAt = createdAt,
                modifiedAt = createdAt,
            )
        }

        fun restore(
            articleId: Long,
            title: String,
            content: String,
            boardId: Long,
            boardArticleCategoryId: Long,
            writerId: Long,
            createdAt: LocalDateTime,
            modifiedAt: LocalDateTime,
        ): Article {
            return Article(
                articleId = articleId,
                title = title,
                content = content,
                boardId = boardId,
                boardArticleCategoryId = boardArticleCategoryId,
                writerId = writerId,
                createdAt = AppDateTime.from(createdAt),
                modifiedAt = AppDateTime.from(modifiedAt)
            )
        }
    }

    override fun toString(): String {
        return "Article(articleId=$articleId, title='$title', content='$content', boardId=$boardId, boarArticleCategoryId=$boardArticleCategoryId, writerId=$writerId, createdAt=$createdAt, modifiedAt=$modifiedAt)"
    }

}
