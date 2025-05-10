package com.ttasjwi.board.system.article.domain

import com.ttasjwi.board.system.article.domain.mapper.ArticleInfiniteScrollReadQueryMapper
import com.ttasjwi.board.system.article.domain.processor.ArticleInfiniteScrollReadProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
class ArticleInfiniteScrollReadUseCaseImpl(
    private val queryMapper: ArticleInfiniteScrollReadQueryMapper,
    private val processor: ArticleInfiniteScrollReadProcessor,
) : ArticleInfiniteScrollReadUseCase {

    override fun readAllInfiniteScroll(request: ArticleInfiniteScrollReadRequest): ArticleInfiniteScrollReadResponse {
        val query = queryMapper.mapToQuery(request)
        return processor.readAllInfiniteScroll(query)
    }
}
