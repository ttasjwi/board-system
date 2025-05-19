package com.ttasjwi.board.system.articlelike.domain

import com.ttasjwi.board.system.articlelike.domain.mapper.ArticleLikeCreateCommandMapper
import com.ttasjwi.board.system.articlelike.domain.model.ArticleLike
import com.ttasjwi.board.system.articlelike.domain.processor.ArticleLikeCreateProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleLikeCreateUseCaseImpl(
    private val commandMapper: ArticleLikeCreateCommandMapper,
    private val processor: ArticleLikeCreateProcessor,
) : ArticleLikeCreateUseCase {

    override fun like(articleId: Long): ArticleLikeCreateResponse {
        val command = commandMapper.mapToCommand(articleId)
        val articleLike = processor.like(command)
        return makeResponse(articleLike)
    }

    private fun makeResponse(articleLike: ArticleLike): ArticleLikeCreateResponse {
        return ArticleLikeCreateResponse(
            articleLikeId = articleLike.articleLikeId.toString(),
            articleId = articleLike.articleId.toString(),
            userId = articleLike.userId.toString(),
            createdAt = articleLike.createdAt.toZonedDateTime()
        )
    }
}
