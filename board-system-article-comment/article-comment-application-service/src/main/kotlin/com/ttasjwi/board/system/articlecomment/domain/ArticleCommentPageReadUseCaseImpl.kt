package com.ttasjwi.board.system.articlecomment.domain

import com.ttasjwi.board.system.articlecomment.domain.mapper.ArticleCommentPageReadQueryMapper
import com.ttasjwi.board.system.articlecomment.domain.processor.ArticleCommentPageReadProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleCommentPageReadUseCaseImpl(
    private val queryMapper: ArticleCommentPageReadQueryMapper,
    private val processor: ArticleCommentPageReadProcessor
) : ArticleCommentPageReadUseCase {

    override fun readAllPage(request: ArticleCommentPageReadRequest): ArticleCommentPageReadResponse {
        val query = queryMapper.mapToQuery(request)
        return processor.readAllPage(query)
    }
}
