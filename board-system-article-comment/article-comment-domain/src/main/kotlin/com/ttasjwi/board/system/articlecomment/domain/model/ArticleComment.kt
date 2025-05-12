package com.ttasjwi.board.system.articlecomment.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

class ArticleComment
internal constructor(
    val articleCommentId: Long,
    content: String,
    val articleId: Long,
    val rootParentCommentId: Long,
    val writerId: Long,
    val writerNickname: String,
    val targetCommentWriterId: Long?,
    val targetCommentWriterNickname: String?,
    deleteStatus: ArticleCommentDeleteStatus,
    val createdAt: AppDateTime,
    modifiedAt: AppDateTime,
) {

    var content: String = content
        private set

    var deleteStatus: ArticleCommentDeleteStatus = deleteStatus
        private set

    var modifiedAt: AppDateTime = modifiedAt
        private set

    companion object {

        fun create(
            articleCommentId: Long,
            content: String,
            articleId: Long,
            rootParentCommentId: Long,
            writerId: Long,
            writerNickname: String,
            targetCommentWriterId: Long?,
            targetCommentWriterNickname: String?,
            createdAt: AppDateTime,
        ): ArticleComment {
            return ArticleComment(
                articleCommentId = articleCommentId,
                content = content,
                articleId = articleId,
                rootParentCommentId = rootParentCommentId,
                writerId = writerId,
                writerNickname = writerNickname,
                targetCommentWriterId = targetCommentWriterId,
                targetCommentWriterNickname = targetCommentWriterNickname,
                deleteStatus = ArticleCommentDeleteStatus.NOT_DELETED,
                createdAt = createdAt,
                modifiedAt = createdAt
            )
        }

        fun restore(
            articleCommentId: Long,
            content: String,
            articleId: Long,
            rootParentCommentId: Long,
            writerId: Long,
            writerNickname: String,
            targetCommentWriterId: Long?,
            targetCommentWriterNickname: String?,
            deleteStatus: ArticleCommentDeleteStatus,
            createdAt: LocalDateTime,
            modifiedAt: LocalDateTime,
        ): ArticleComment {
            return ArticleComment(
                articleCommentId = articleCommentId,
                content = content,
                articleId = articleId,
                rootParentCommentId = rootParentCommentId,
                writerId = writerId,
                writerNickname = writerNickname,
                targetCommentWriterId = targetCommentWriterId,
                targetCommentWriterNickname = targetCommentWriterNickname,
                deleteStatus = deleteStatus,
                createdAt = AppDateTime.from(createdAt),
                modifiedAt = AppDateTime.from(modifiedAt),
            )
        }
    }

    fun isRootComment(): Boolean {
        return this.articleCommentId == this.rootParentCommentId
    }

    fun deleteByWriter() {
        this.deleteStatus = ArticleCommentDeleteStatus.DELETED_BY_WRITER
    }

    fun isDeleted(): Boolean {
        return this.deleteStatus != ArticleCommentDeleteStatus.NOT_DELETED
    }

    override fun toString(): String {
        return "ArticleComment(articleCommentId=$articleCommentId, articleId=$articleId, rootParentCommentId=$rootParentCommentId, writerId=$writerId, writerNickname='$writerNickname', targetCommentWriterId=$targetCommentWriterId, targetCommentWriterNickname=$targetCommentWriterNickname, createdAt=$createdAt, content='$content', deleteStatus=$deleteStatus, modifiedAt=$modifiedAt)"
    }
}
