package com.ttasjwi.board.system.article.domain

import com.ttasjwi.board.system.article.domain.mapper.ArticlePageReadQueryMapper
import com.ttasjwi.board.system.article.domain.processor.ArticlePageReadProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
class ArticlePageReadUseCaseImpl(
    private val queryMapper: ArticlePageReadQueryMapper,
    private val processor: ArticlePageReadProcessor,
): ArticlePageReadUseCase {

    override fun readAllPage(request: ArticlePageReadRequest): ArticlePageReadResponse {
        val query = queryMapper.mapToQuery(request)
        return processor.readAllPage(query)
    }
}
