package com.ttasjwi.board.system.article.domain.processor

import com.ttasjwi.board.system.article.domain.ArticlePageElement
import com.ttasjwi.board.system.article.domain.ArticlePageReadResponse
import com.ttasjwi.board.system.article.domain.dto.ArticlePageReadQuery
import com.ttasjwi.board.system.article.domain.model.Article
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.article.domain.port.BoardArticleCountPersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.page.PagingInfo
import com.ttasjwi.board.system.common.page.calculateOffset

@ApplicationProcessor
class ArticlePageReadProcessor(
    private val articlePersistencePort: ArticlePersistencePort,
    private val boardArticleCountPersistencePort: BoardArticleCountPersistencePort
) {

    companion object {
        internal const val ARTICLE_PAGE_GROUP_SIZE = 10L
    }

    fun readAllPage(query: ArticlePageReadQuery): ArticlePageReadResponse {
        val totalArticleCount = readArticleCount(query.boardId)
        val articles = readArticles(query)

        val pagingInfo = PagingInfo.from(
            page = query.page,
            pageSize = query.pageSize,
            totalCount = totalArticleCount,
            pageGroupSize = ARTICLE_PAGE_GROUP_SIZE
        )

        return makeResponse(pagingInfo, articles)
    }

    private fun readArticleCount(boardId: Long): Long {
        return boardArticleCountPersistencePort.findByIdOrNull(boardId)?.articleCount ?: 0L
    }

    private fun readArticles(query: ArticlePageReadQuery) =
        articlePersistencePort.findAllPage(
            boardId = query.boardId,
            offSet = calculateOffset(
                page = query.page,
                pageSize = query.pageSize
            ),
            pageSize = query.pageSize
        )

    private fun makeResponse(
        pagingInfo: PagingInfo,
        articles: List<Article>
    ) = ArticlePageReadResponse(
        page = pagingInfo.page,
        pageSize = pagingInfo.pageSize,
        pageGroupSize = pagingInfo.pageGroupSize,
        pageGroupStart = pagingInfo.pageGroupStart,
        pageGroupEnd = pagingInfo.pageGroupEnd,
        hasNextPage = pagingInfo.hasNextPage,
        hasNextPageGroup = pagingInfo.hasNextPageGroup,
        articles = articles.map { it.toArticlePageElement() }
    )

    private fun Article.toArticlePageElement(): ArticlePageElement {
        return ArticlePageElement(
            articleId = this.articleId.toString(),
            title = this.title,
            content = this.content,
            boardId = this.boardId.toString(),
            articleCategoryId = this.articleCategoryId.toString(),
            writerId = this.writerId.toString(),
            writerNickname = this.writerNickname,
            createdAt = this.createdAt.toZonedDateTime(),
            modifiedAt = this.modifiedAt.toZonedDateTime(),
        )
    }
}
