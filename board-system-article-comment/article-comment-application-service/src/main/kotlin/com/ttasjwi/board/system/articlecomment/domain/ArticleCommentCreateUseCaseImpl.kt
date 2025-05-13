package com.ttasjwi.board.system.articlecomment.domain

import com.ttasjwi.board.system.articlecomment.domain.mapper.ArticleCommentCreateCommandMapper
import com.ttasjwi.board.system.articlecomment.domain.model.ArticleComment
import com.ttasjwi.board.system.articlecomment.domain.processor.ArticleCommentCreateProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleCommentCreateUseCaseImpl(
    private val commandMapper: ArticleCommentCreateCommandMapper,
    private val processor: ArticleCommentCreateProcessor,
) : ArticleCommentCreateUseCase {

    override fun create(request: ArticleCommentCreateRequest): ArticleCommentCreateResponse {
        val command = commandMapper.mapToCommand(request)
        val articleComment = processor.create(command)
        return makeResponse(articleComment)
    }

    private fun makeResponse(articleComment: ArticleComment): ArticleCommentCreateResponse {
        return ArticleCommentCreateResponse(
            articleCommentId = articleComment.articleCommentId.toString(),
            content = articleComment.content,
            articleId = articleComment.articleId.toString(),
            rootParentCommentId = articleComment.rootParentCommentId.toString(),
            writerId = articleComment.writerId.toString(),
            writerNickname = articleComment.writerNickname,
            parentCommentWriterId = articleComment.parentCommentWriterId?.toString(),
            parentCommentWriterNickname = articleComment.parentCommentWriterNickname,
            deleteStatus = articleComment.deleteStatus.name,
            createdAt = articleComment.createdAt.toZonedDateTime(),
            modifiedAt = articleComment.modifiedAt.toZonedDateTime(),
        )
    }
}
