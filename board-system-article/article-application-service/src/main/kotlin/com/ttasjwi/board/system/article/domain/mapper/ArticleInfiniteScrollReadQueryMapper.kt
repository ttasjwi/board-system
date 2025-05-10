package com.ttasjwi.board.system.article.domain.mapper

import com.ttasjwi.board.system.article.domain.ArticleInfiniteScrollReadRequest
import com.ttasjwi.board.system.article.domain.dto.ArticleInfiniteScrollReadQuery
import com.ttasjwi.board.system.article.domain.exception.InvalidArticlePageSizeException
import com.ttasjwi.board.system.common.annotation.component.ApplicationQueryMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector

@ApplicationQueryMapper
class ArticleInfiniteScrollReadQueryMapper {

    companion object {
        internal const val MIN_PAGE_SIZE = 1L
        internal const val MAX_PAGE_SIZE = 50L
    }

    fun mapToQuery(request: ArticleInfiniteScrollReadRequest): ArticleInfiniteScrollReadQuery {
        val exceptionCollector = ValidationExceptionCollector()
        val boardId = getBoardId(request.boardId, exceptionCollector)
        val pageSize = getPageSize(request.pageSize, exceptionCollector)

        exceptionCollector.throwIfNotEmpty()

        return ArticleInfiniteScrollReadQuery(
            boardId = boardId!!,
            pageSize = pageSize!!,
            lastArticleId = request.lastArticleId
        )
    }

    private fun getBoardId(boardId: Long?, exceptionCollector: ValidationExceptionCollector): Long? {
        if (boardId == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("boardId"))
            return null
        }
        return boardId
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
