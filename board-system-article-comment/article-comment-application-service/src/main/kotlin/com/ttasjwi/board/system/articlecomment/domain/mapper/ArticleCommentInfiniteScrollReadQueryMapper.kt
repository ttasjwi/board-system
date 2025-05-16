package com.ttasjwi.board.system.articlecomment.domain.mapper

import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentInfiniteScrollReadRequest
import com.ttasjwi.board.system.articlecomment.domain.dto.ArticleCommentInfiniteScrollReadQuery
import com.ttasjwi.board.system.articlecomment.domain.exception.InvalidArticleCommentPageSizeException
import com.ttasjwi.board.system.common.annotation.component.ApplicationQueryMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector

@ApplicationQueryMapper
internal class ArticleCommentInfiniteScrollReadQueryMapper {

    companion object {
        internal const val MIN_PAGE_SIZE = 1L
        internal const val MAX_PAGE_SIZE = 50L
    }

    fun mapToQuery(request: ArticleCommentInfiniteScrollReadRequest): ArticleCommentInfiniteScrollReadQuery {
        val exceptionCollector = ValidationExceptionCollector()
        val articleId = getArticleId(request.articleId, exceptionCollector)
        val pageSize = getPageSize(request.pageSize, exceptionCollector)
        exceptionCollector.throwIfNotEmpty()

        return ArticleCommentInfiniteScrollReadQuery(
            articleId = articleId!!,
            pageSize = pageSize!!,
            lastRootParentCommentId = request.lastRootParentCommentId,
            lastCommentId = request.lastCommentId,
        )
    }

    private fun getArticleId(articleId: Long?, exceptionCollector: ValidationExceptionCollector): Long? {
        if (articleId == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("articleId"))
            return null
        }
        return articleId
    }

    private fun getPageSize(pageSize: Long?, exceptionCollector: ValidationExceptionCollector): Long? {
        if (pageSize == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("pageSize"))
            return null
        }
        if (pageSize < MIN_PAGE_SIZE || pageSize > MAX_PAGE_SIZE) {
            exceptionCollector.addCustomExceptionOrThrow(InvalidArticleCommentPageSizeException(pageSize, MIN_PAGE_SIZE, MAX_PAGE_SIZE))
            return null
        }
        return pageSize
    }
}
