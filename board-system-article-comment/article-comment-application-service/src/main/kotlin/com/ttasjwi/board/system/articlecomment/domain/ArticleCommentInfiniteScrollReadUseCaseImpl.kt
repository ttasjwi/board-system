package com.ttasjwi.board.system.articlecomment.domain

import com.ttasjwi.board.system.articlecomment.domain.mapper.ArticleCommentInfiniteScrollReadQueryMapper
import com.ttasjwi.board.system.articlecomment.domain.processor.ArticleCommentInfiniteScrollReadProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleCommentInfiniteScrollReadUseCaseImpl(
    private val queryMapper: ArticleCommentInfiniteScrollReadQueryMapper,
    private val processor: ArticleCommentInfiniteScrollReadProcessor
) : ArticleCommentInfiniteScrollReadUseCase {

    override fun readAllInfiniteScroll(request: ArticleCommentInfiniteScrollReadRequest): ArticleCommentInfiniteScrollReadResponse {
        val query = queryMapper.mapToQuery(request)
        return processor.readAllInfiniteScroll(query)
    }
}
