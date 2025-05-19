package com.ttasjwi.board.system.articlelike.domain

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleLikeCancelCommand
import com.ttasjwi.board.system.articlelike.domain.mapper.ArticleLikeCancelCommandMapper
import com.ttasjwi.board.system.articlelike.domain.processor.ArticleLikeCancelProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleLikeCancelUseCaseImpl(
    private val commandMapper: ArticleLikeCancelCommandMapper,
    private val processor: ArticleLikeCancelProcessor
) : ArticleLikeCancelUseCase {

    override fun cancelLike(articleId: Long): ArticleLikeCancelResponse {
        val command = commandMapper.mapToCommand(articleId)
        processor.cancelLike(command)
        return makeResponse(command)
    }

    private fun makeResponse(command: ArticleLikeCancelCommand): ArticleLikeCancelResponse {
        return ArticleLikeCancelResponse(
            articleId = command.articleId.toString(),
            userId = command.user.userId.toString(),
            canceledAt = command.currentTime.toZonedDateTime()
        )
    }
}
