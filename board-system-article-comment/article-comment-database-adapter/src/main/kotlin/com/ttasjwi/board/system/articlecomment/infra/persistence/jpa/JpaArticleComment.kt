package com.ttasjwi.board.system.articlecomment.infra.persistence.jpa

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleComment
import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentDeleteStatus
import jakarta.persistence.*
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

    @Column(name = "parent_comment_writer_id")
    val parentCommentWriterId: Long?,

    @Column(name = "parent_comment_writer_nickname")
    val parentCommentWriterNickname: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "delete_status")
    var deleteStaus: ArticleCommentDeleteStatus,

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
                parentCommentWriterId = articleComment.parentCommentWriterId,
                parentCommentWriterNickname = articleComment.parentCommentWriterNickname,
                deleteStaus = articleComment.deleteStatus,
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
            parentCommentWriterId = this.parentCommentWriterId,
            parentCommentWriterNickname = this.parentCommentWriterNickname,
            deleteStatus = this.deleteStaus,
            createdAt = this.createdAt,
            modifiedAt = this.modifiedAt,
        )
    }
}
