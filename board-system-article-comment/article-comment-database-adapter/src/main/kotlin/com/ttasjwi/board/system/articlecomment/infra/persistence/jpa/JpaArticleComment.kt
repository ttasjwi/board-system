package com.ttasjwi.board.system.articlecomment.infra.persistence.jpa

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleComment
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "article_comments")
class JpaArticleComment(

    @Id
    @Column(name = "article_comment_id")
    val articleCommentId: Long,

    @Column(name = "content")
    var content: String,

    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "root_parent_comment_id")
    val rootParentCommentId: Long,

    @Column(name = "writer_id")
    val writerId: Long,

    @Column(name = "writer_nickname")
    val writerNickname: String,

    @Column(name = "target_comment_writer_id")
    val targetCommentWriterId: Long?,

    @Column(name = "target_comment_writer_nickname")
    val targetCommentWriterNickname: String?,

    @Column(name = "deleted")
    var deleted: Boolean,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "modified_at")
    var modifiedAt: LocalDateTime,
) {

    companion object {

        fun from(articleComment: ArticleComment): JpaArticleComment {
            return JpaArticleComment(
                articleCommentId = articleComment.articleCommentId,
                content = articleComment.content,
                articleId = articleComment.articleId,
                rootParentCommentId = articleComment.rootParentCommentId,
                writerId = articleComment.writerId,
                writerNickname = articleComment.writerNickname,
                targetCommentWriterId = articleComment.targetCommentWriterId,
                targetCommentWriterNickname = articleComment.targetCommentWriterNickname,
                deleted = articleComment.deleted,
                createdAt = articleComment.createdAt.toLocalDateTime(),
                modifiedAt = articleComment.modifiedAt.toLocalDateTime()
            )
        }
    }

    fun restoreDomain(): ArticleComment {
        return ArticleComment.restore(
            articleCommentId = this.articleCommentId,
            content = this.content,
            articleId = this.articleId,
            rootParentCommentId = this.rootParentCommentId,
            writerId = this.writerId,
            writerNickname = this.writerNickname,
            targetCommentWriterId = this.targetCommentWriterId,
            targetCommentWriterNickname = this.targetCommentWriterNickname,
            deleted = this.deleted,
            createdAt = this.createdAt,
            modifiedAt = this.modifiedAt,
        )
    }
}
