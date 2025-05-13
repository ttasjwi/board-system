package com.ttasjwi.board.system.articlecomment.domain

import com.ttasjwi.board.system.articlecomment.domain.exception.ArticleCommentNotFoundException
import com.ttasjwi.board.system.articlecomment.domain.model.ArticleComment
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentPersistencePort
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
class ArticleCommentReadUseCaseImpl(
    private val articleCommentPersistencePort: ArticleCommentPersistencePort
) : ArticleCommentReadUseCase {

    override fun read(commentId: Long): ArticleCommentReadResponse {
        val articleComment = getComment(commentId)
        return makeResponse(articleComment)
    }

    private fun getComment(commentId: Long): ArticleComment {
        val articleComment = articleCommentPersistencePort.findById(commentId)

        // 저장된 댓글이 없거나, 논리적으로 삭제된 댓글일 경우 반환하지 않고 예외 발생
        if (articleComment == null || articleComment.isDeleted()) {
            throw ArticleCommentNotFoundException(commentId)
        }
        return articleComment
    }

    private fun makeResponse(articleComment: ArticleComment): ArticleCommentReadResponse {
        return ArticleCommentReadResponse(
            articleCommentId = articleComment.articleCommentId.toString(),
            content = articleComment.content,
            articleId = articleComment.articleId.toString(),
            rootParentCommentId = articleComment.rootParentCommentId.toString(),
            writerId = articleComment.writerId.toString(),
            writerNickname = articleComment.writerNickname,
            parentCommentWriterId = articleComment.parentCommentWriterId?.toString(),
            parentCommentWriterNickname = articleComment.parentCommentWriterNickname,
            createdAt = articleComment.createdAt.toZonedDateTime(),
            modifiedAt = articleComment.modifiedAt.toZonedDateTime(),
        )
    }
}
