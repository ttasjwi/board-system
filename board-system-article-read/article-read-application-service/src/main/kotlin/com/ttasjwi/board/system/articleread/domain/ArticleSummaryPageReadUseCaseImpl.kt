package com.ttasjwi.board.system.articleread.domain

import com.ttasjwi.board.system.articleread.domain.mapper.ArticleSummaryPageReadQueryMapper
import com.ttasjwi.board.system.articleread.domain.processor.ArticleSummaryPageReadProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleSummaryPageReadUseCaseImpl(
    private val queryMapper: ArticleSummaryPageReadQueryMapper,
    private val processor: ArticleSummaryPageReadProcessor
): ArticleSummaryPageReadUseCase {

    override fun readAllPage(request: ArticleSummaryPageReadRequest): ArticleSummaryPageReadResponse {
        val query = queryMapper.mapToQuery(request)
        return processor.readAllPage(query)
    }
}
