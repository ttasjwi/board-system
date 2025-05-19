package com.ttasjwi.board.system.articlelike.domain

import com.ttasjwi.board.system.articlelike.domain.mapper.ArticleDislikeCreateCommandMapper
import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislike
import com.ttasjwi.board.system.articlelike.domain.processor.ArticleDislikeCreateProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleDislikeCreateUseCaseImpl(
    private val commandMapper: ArticleDislikeCreateCommandMapper,
    private val processor: ArticleDislikeCreateProcessor,
) : ArticleDislikeCreateUseCase {

    override fun dislike(articleId: Long): ArticleDislikeCreateResponse {
        val command = commandMapper.mapToCommand(articleId)
        val articleDislike = processor.dislike(command)
        return makeResponse(articleDislike)
    }

    private fun makeResponse(articleLike: ArticleDislike): ArticleDislikeCreateResponse {
        return ArticleDislikeCreateResponse(
            articleDislikeId = articleLike.articleDislikeId.toString(),
            articleId = articleLike.articleId.toString(),
            userId = articleLike.userId.toString(),
            createdAt = articleLike.createdAt.toZonedDateTime()
        )
    }
}
