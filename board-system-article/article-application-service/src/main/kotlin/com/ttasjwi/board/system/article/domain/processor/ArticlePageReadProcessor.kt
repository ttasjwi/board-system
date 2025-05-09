package com.ttasjwi.board.system.article.domain.processor

import com.ttasjwi.board.system.article.domain.ArticlePageElement
import com.ttasjwi.board.system.article.domain.ArticlePageReadResponse
import com.ttasjwi.board.system.article.domain.dto.ArticlePageReadQuery
import com.ttasjwi.board.system.article.domain.model.Article
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.page.calculateOffSet
import com.ttasjwi.board.system.common.page.calculatePageLimit

@ApplicationProcessor
class ArticlePageReadProcessor(
    private val articlePersistencePort: ArticlePersistencePort
) {

    fun readAllPage(query: ArticlePageReadQuery): ArticlePageReadResponse {
        val articleCount = readArticleCount(query)
        val articles = readArticles(query)
        return ArticlePageReadResponse(
            articleCount = articleCount,
            articles = articles.map { it.toArticlePageElement() }
        )
    }

    private fun readArticleCount(query: ArticlePageReadQuery) = articlePersistencePort.count(
        boardId = query.boardId,
        limit = calculatePageLimit(
            page = query.page,
            pageSize = query.pageSize,
            movablePageCount = 10
        )
    )

    private fun readArticles(query: ArticlePageReadQuery) =
        articlePersistencePort.findAllPage(
            boardId = query.boardId,
            offSet = calculateOffSet(
                page = query.page,
                pageSize = query.pageSize
            ),
            pageSize = query.pageSize
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
