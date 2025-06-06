package com.ttasjwi.board.system.article.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

class Article
internal constructor(
    val articleId: Long,
    title: String,
    content: String,
    val boardId: Long,
    articleCategoryId: Long,
    val writerId: Long,
    val writerNickname: String,
    val createdAt: AppDateTime,
    modifiedAt: AppDateTime,
) {

    var title: String = title
        private set

    var content: String = content
        private set

    var articleCategoryId: Long = articleCategoryId
        private set

    var modifiedAt: AppDateTime = modifiedAt
        private set

    companion object {

        fun create(
            articleId: Long,
            title: String,
            content: String,
            boardId: Long,
            articleCategoryId: Long,
            writerId: Long,
            writerNickname: String,
            createdAt: AppDateTime,
        ): Article {
            return Article(
                articleId = articleId,
                title = title,
                content = content,
                boardId = boardId,
                articleCategoryId = articleCategoryId,
                writerId = writerId,
                writerNickname = writerNickname,
                createdAt = createdAt,
                modifiedAt = createdAt,
            )
        }

        fun restore(
            articleId: Long,
            title: String,
            content: String,
            boardId: Long,
            articleCategoryId: Long,
            writerId: Long,
            writerNickname: String,
            createdAt: LocalDateTime,
            modifiedAt: LocalDateTime,
        ): Article {
            return Article(
                articleId = articleId,
                title = title,
                content = content,
                boardId = boardId,
                articleCategoryId = articleCategoryId,
                writerId = writerId,
                writerNickname = writerNickname,
                createdAt = AppDateTime.from(createdAt),
                modifiedAt = AppDateTime.from(modifiedAt)
            )
        }
    }

    fun isWriter(userId: Long): Boolean {
        return this.writerId == userId
    }

    fun update(title: String, content: String, modifiedAt: AppDateTime): UpdateResult {
        if (title == this.title && content == this.content) {
            return UpdateResult.UNCHANGED
        }
        this.title = title
        this.content = content
        this.modifiedAt = modifiedAt
        return UpdateResult.CHANGED
    }

    enum class UpdateResult {
        CHANGED, UNCHANGED
    }

    override fun toString(): String {
        return "Article(articleId=$articleId, title='$title', content='$content', boardId=$boardId, articleCategoryId=$articleCategoryId, writerId=$writerId, writerNickname='$writerNickname', createdAt=$createdAt, modifiedAt=$modifiedAt)"
    }

}
