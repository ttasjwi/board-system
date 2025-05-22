package com.ttasjwi.board.system.articleview.domain

import com.ttasjwi.board.system.articleview.domain.mapper.ArticleViewCountIncreaseCommandMapper
import com.ttasjwi.board.system.articleview.domain.processor.ArticleViewCountIncreaseProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleViewCountIncreaseUseCaseImpl(
    private val commandMapper: ArticleViewCountIncreaseCommandMapper,
    private val processor: ArticleViewCountIncreaseProcessor,
) : ArticleViewCountIncreaseUseCase {

    override fun increase(articleId: Long) {
        val command = commandMapper.mapToCommand(articleId)
        processor.increase(command)
    }
}
