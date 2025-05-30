package com.ttasjwi.board.system.articleread.domain.mapper

import com.ttasjwi.board.system.articleread.domain.ArticleSummaryPageReadRequest
import com.ttasjwi.board.system.articleread.domain.dto.ArticleSummaryPageReadQuery
import com.ttasjwi.board.system.articleread.domain.exception.InvalidArticlePageSizeException
import com.ttasjwi.board.system.common.annotation.component.ApplicationQueryMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector

@ApplicationQueryMapper
class ArticleSummaryPageReadQueryMapper {

    companion object {
        internal const val MIN_PAGE_SIZE = 1L
        internal const val MAX_PAGE_SIZE = 50L
    }

    fun mapToQuery(request: ArticleSummaryPageReadRequest): ArticleSummaryPageReadQuery {
        val exceptionCollector = ValidationExceptionCollector()
        val page = getPage(request.page, exceptionCollector)
        val pageSize = getPageSize(request.pageSize, exceptionCollector)

        exceptionCollector.throwIfNotEmpty()

        return ArticleSummaryPageReadQuery(
            boardId = request.boardId,
            page = page!!,
            pageSize = pageSize!!
        )
    }

    private fun getPage(page: Long?, exceptionCollector: ValidationExceptionCollector): Long? {
        if (page == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("page"))
            return null
        }
        return page
    }

    private fun getPageSize(pageSize: Long?, exceptionCollector: ValidationExceptionCollector): Long? {
        if (pageSize == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("pageSize"))
            return null
        }
        if (pageSize < MIN_PAGE_SIZE || pageSize > MAX_PAGE_SIZE) {
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
