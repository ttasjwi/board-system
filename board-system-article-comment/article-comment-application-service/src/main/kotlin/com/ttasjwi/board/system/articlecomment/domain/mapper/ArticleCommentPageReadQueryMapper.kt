package com.ttasjwi.board.system.articlecomment.domain.mapper

import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentPageReadRequest
import com.ttasjwi.board.system.articlecomment.domain.dto.ArticleCommentPageReadQuery
import com.ttasjwi.board.system.articlecomment.domain.exception.InvalidArticleCommentPageException
import com.ttasjwi.board.system.articlecomment.domain.exception.InvalidArticleCommentPageSizeException
import com.ttasjwi.board.system.common.annotation.component.ApplicationQueryMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector

@ApplicationQueryMapper
internal class ArticleCommentPageReadQueryMapper {

    companion object {
        internal const val MIN_PAGE = 1L
        internal const val MAX_PAGE = 100L
        internal const val MIN_PAGE_SIZE = 1L
        internal const val MAX_PAGE_SIZE = 50L
    }

    fun mapToQuery(request: ArticleCommentPageReadRequest): ArticleCommentPageReadQuery {
        val exceptionCollector = ValidationExceptionCollector()
        val articleId = getArticleId(request.articleId, exceptionCollector)
        val page = getPage(request.page, exceptionCollector)
        val pageSize = getPageSize(request.pageSize, exceptionCollector)
        exceptionCollector.throwIfNotEmpty()

        return ArticleCommentPageReadQuery(
            articleId = articleId!!,
            page = page!!,
            pageSize = pageSize!!
        )
    }

    private fun getArticleId(articleId: Long?, exceptionCollector: ValidationExceptionCollector): Long? {
        if (articleId == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("articleId"))
            return null
        }
        return articleId
    }

    private fun getPage(page: Long?, exceptionCollector: ValidationExceptionCollector): Long? {
        if (page == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("page"))
            return null
        }
        if (page < MIN_PAGE || page > MAX_PAGE) {
            exceptionCollector.addCustomExceptionOrThrow(
                InvalidArticleCommentPageException(page = page, minPage =  MIN_PAGE, maxPage = MAX_PAGE)
            )
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
            exceptionCollector.addCustomExceptionOrThrow(InvalidArticleCommentPageSizeException(pageSize, MIN_PAGE_SIZE, MAX_PAGE_SIZE))
            return null
        }
        return pageSize
    }
}
