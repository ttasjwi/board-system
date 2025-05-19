package com.ttasjwi.board.system.articlelike.domain

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleDislikeCancelCommand
import com.ttasjwi.board.system.articlelike.domain.mapper.ArticleDislikeCancelCommandMapper
import com.ttasjwi.board.system.articlelike.domain.processor.ArticleDislikeCancelProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleDislikeCancelUseCaseImpl(
    private val commandMapper: ArticleDislikeCancelCommandMapper,
    private val processor: ArticleDislikeCancelProcessor
) : ArticleDislikeCancelUseCase {

    override fun cancelDislike(articleId: Long): ArticleDislikeCancelResponse {
        val command = commandMapper.mapToCommand(articleId)
        processor.cancelDislike(command)
        return makeResponse(command)
    }

    private fun makeResponse(command: ArticleDislikeCancelCommand): ArticleDislikeCancelResponse {
        return ArticleDislikeCancelResponse(
            articleId = command.articleId.toString(),
            userId = command.user.userId.toString(),
            canceledAt = command.currentTime.toZonedDateTime()
        )
    }
}
