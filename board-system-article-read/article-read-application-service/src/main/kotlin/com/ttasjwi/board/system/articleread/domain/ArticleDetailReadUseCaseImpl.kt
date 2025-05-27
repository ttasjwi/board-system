package com.ttasjwi.board.system.articleread.domain

import com.ttasjwi.board.system.articleread.domain.mapper.ArticleDetailReadQueryMapper
import com.ttasjwi.board.system.articleread.domain.processor.ArticleDetailReadProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleDetailReadUseCaseImpl(
    private val queryMapper: ArticleDetailReadQueryMapper,
    private val processor: ArticleDetailReadProcessor
) : ArticleDetailReadUseCase {

    override fun read(articleId: Long): ArticleDetailReadResponse {
        val query = queryMapper.mapToQuery(articleId)
        return processor.read(query)
    }
}
