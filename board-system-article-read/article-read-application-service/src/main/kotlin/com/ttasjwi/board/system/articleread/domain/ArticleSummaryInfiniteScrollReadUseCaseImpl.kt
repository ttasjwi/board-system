package com.ttasjwi.board.system.articleread.domain

import com.ttasjwi.board.system.articleread.domain.mapper.ArticleSummaryInfiniteScrollReadQueryMapper
import com.ttasjwi.board.system.articleread.domain.processor.ArticleSummaryInfiniteScrollReadProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleSummaryInfiniteScrollReadUseCaseImpl(
    private val queryMapper: ArticleSummaryInfiniteScrollReadQueryMapper,
    private val processor: ArticleSummaryInfiniteScrollReadProcessor,
) : ArticleSummaryInfiniteScrollReadUseCase {

    override fun readAllInfiniteScroll(request: ArticleSummaryInfiniteScrollReadRequest): ArticleSummaryInfiniteScrollReadResponse {
        val query = queryMapper.mapToQuery(request)
        return processor.readAllInfiniteScroll(query)
    }
}
