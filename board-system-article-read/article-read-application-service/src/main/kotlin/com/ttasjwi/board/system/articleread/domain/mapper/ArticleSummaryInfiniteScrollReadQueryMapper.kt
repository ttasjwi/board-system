package com.ttasjwi.board.system.articleread.domain.mapper

import com.ttasjwi.board.system.articleread.domain.ArticleSummaryInfiniteScrollReadRequest
import com.ttasjwi.board.system.articleread.domain.dto.ArticleSummaryInfiniteScrollReadQuery
import com.ttasjwi.board.system.articleread.domain.exception.InvalidArticlePageSizeException
import com.ttasjwi.board.system.common.annotation.component.ApplicationQueryMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector

@ApplicationQueryMapper
internal class ArticleSummaryInfiniteScrollReadQueryMapper {

    companion object {
        internal const val MIN_PAGE_SIZE = 1L
        internal const val MAX_PAGE_SIZE = 50L
    }

    fun mapToQuery(request: ArticleSummaryInfiniteScrollReadRequest): ArticleSummaryInfiniteScrollReadQuery {
        val exceptionCollector = ValidationExceptionCollector()
        val pageSize = getPageSize(request.pageSize, exceptionCollector)

        exceptionCollector.throwIfNotEmpty()

        return ArticleSummaryInfiniteScrollReadQuery(
            boardId = request.boardId,
            pageSize = pageSize!!,
            lastArticleId = request.lastArticleId
        )
    }

    private fun getPageSize(pageSize: Long?, exceptionCollector: ValidationExceptionCollector): Long? {
        if (pageSize == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("pageSize"))
            return null
        }
        if (pageSize > MAX_PAGE_SIZE || pageSize < MIN_PAGE_SIZE) {
            exceptionCollector.addCustomExceptionOrThrow(
                InvalidArticlePageSizeException(
                    pageSize = pageSize,
                    minPageSize = MIN_PAGE_SIZE,
                    maxPageSize = MAX_PAGE_SIZE
                )
            )
            return null
        }
        return pageSize
    }
}
