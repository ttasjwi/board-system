package com.ttasjwi.board.system.article.domain.mapper

import com.ttasjwi.board.system.article.domain.ArticlePageReadRequest
import com.ttasjwi.board.system.article.domain.dto.ArticlePageReadQuery
import com.ttasjwi.board.system.article.domain.exception.InvalidArticlePageException
import com.ttasjwi.board.system.article.domain.exception.InvalidArticlePageSizeException
import com.ttasjwi.board.system.common.annotation.component.ApplicationQueryMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector

@ApplicationQueryMapper
class ArticlePageReadQueryMapper {

    companion object {
        internal const val MIN_PAGE = 1L
        internal const val MAX_PAGE = 100L
        internal const val MIN_PAGE_SIZE = 1L
        internal const val MAX_PAGE_SIZE = 50L
    }

    fun mapToQuery(request: ArticlePageReadRequest): ArticlePageReadQuery {
        val exceptionCollector = ValidationExceptionCollector()
        val boardId = getBoardId(request.boardId, exceptionCollector)
        val page = getPage(request.page, exceptionCollector)
        val pageSize = getPageSize(request.pageSize, exceptionCollector)

        exceptionCollector.throwIfNotEmpty()

        return ArticlePageReadQuery(
            boardId = boardId!!,
            page = page!!,
            pageSize = pageSize!!
        )
    }

    private fun getBoardId(boardId: Long?, exceptionCollector: ValidationExceptionCollector): Long? {
        if (boardId == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("boardId"))
            return null
        }
        return boardId
    }

    private fun getPage(page: Long?, exceptionCollector: ValidationExceptionCollector): Long? {
        if (page == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("page"))
            return null
        }
        if (page < MIN_PAGE || page > MAX_PAGE) {
            exceptionCollector.addCustomExceptionOrThrow(
                InvalidArticlePageException(
                    page = page,
                    minPage = MIN_PAGE,
                    maxPage = MAX_PAGE
                )
            )
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
