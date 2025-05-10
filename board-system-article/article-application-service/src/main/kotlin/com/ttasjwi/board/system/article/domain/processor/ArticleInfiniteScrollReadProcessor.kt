package com.ttasjwi.board.system.article.domain.processor

import com.ttasjwi.board.system.article.domain.ArticleInfiniteScrollItem
import com.ttasjwi.board.system.article.domain.ArticleInfiniteScrollReadResponse
import com.ttasjwi.board.system.article.domain.dto.ArticleInfiniteScrollReadQuery
import com.ttasjwi.board.system.article.domain.model.Article
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor

@ApplicationProcessor
class ArticleInfiniteScrollReadProcessor(
    private val articlePersistencePort: ArticlePersistencePort
) {

    fun readAllInfiniteScroll(query: ArticleInfiniteScrollReadQuery): ArticleInfiniteScrollReadResponse {
        // 실제 요청 건수보다 1건 더 조회
        val limit = query.pageSize + 1
        val findArticles = articlePersistencePort
            .findAllInfiniteScroll(
                boardId = query.boardId,
                limit = limit,
                lastArticleId = query.lastArticleId
            )

        // 실제 limit 건 조회됐다면, 다음 요소가 있음을 의미
        val hasNext = findArticles.size == limit.toInt()

        // 다음 요소가 있다면, 사용자에게 실제 내려줄 데이터는 pageSize 까지이므로 잘라냄
        val articles = if (hasNext) {
            findArticles.subList(0, query.pageSize.toInt())
        } else {
            findArticles
        }

        return ArticleInfiniteScrollReadResponse(
            hasNext = hasNext,
            articles = articles.map { it.toArticleInfiniteScrollItem() }
        )
    }

    private fun Article.toArticleInfiniteScrollItem(): ArticleInfiniteScrollItem {
        return ArticleInfiniteScrollItem(
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
