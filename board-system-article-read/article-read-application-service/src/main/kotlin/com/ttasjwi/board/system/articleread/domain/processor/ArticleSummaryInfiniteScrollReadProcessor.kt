package com.ttasjwi.board.system.articleread.domain.processor

import com.ttasjwi.board.system.articleread.domain.ArticleSummaryInfiniteScrollReadResponse
import com.ttasjwi.board.system.articleread.domain.dto.ArticleSummaryInfiniteScrollReadQuery
import com.ttasjwi.board.system.articleread.domain.model.ArticleSummaryQueryModel
import com.ttasjwi.board.system.articleread.domain.port.ArticleSummaryStorage
import com.ttasjwi.board.system.articleread.domain.port.ArticleViewCountStorage
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor

@ApplicationProcessor
internal class ArticleSummaryInfiniteScrollReadProcessor(
    private val articleSummaryStorage: ArticleSummaryStorage,
    private val articleViewCountStorage: ArticleViewCountStorage
) {

    fun readAllInfiniteScroll(query: ArticleSummaryInfiniteScrollReadQuery): ArticleSummaryInfiniteScrollReadResponse {
        // 실제 요청 건수보다 1건 더 조회
        val limit = query.pageSize + 1

        // 게시글 목록 조회
        val findArticleQueryModels = articleSummaryStorage
            .findAllInfiniteScroll(
                boardId = query.boardId,
                limit = limit,
                lastArticleId = query.lastArticleId
            )

        // 실제 limit 건 조회됐다면, 다음 요소가 있음을 의미
        val hasNext = findArticleQueryModels.size == limit.toInt()

        // 다음 요소가 있다면, 사용자에게 실제 내려줄 데이터는 pageSize 까지이므로 잘라냄
        val articles = if (hasNext) {
            findArticleQueryModels.subList(0, query.pageSize.toInt())
        } else {
            findArticleQueryModels
        }

        // 게시글별 조회수 조회
        val viewCounts = articleViewCountStorage.readViewCounts(articles.map { it.articleId })

        return makeResponse(hasNext = hasNext, articles = articles, viewCounts = viewCounts)
    }

    private fun makeResponse(
        hasNext: Boolean,
        articles: List<ArticleSummaryQueryModel>,
        viewCounts: Map<Long, Long>
    ): ArticleSummaryInfiniteScrollReadResponse {
        return ArticleSummaryInfiniteScrollReadResponse(
            hasNext = hasNext,
            articles = articles.map { it.toResponseItem(viewCounts[it.articleId]!!) }
        )
    }

    private fun ArticleSummaryQueryModel.toResponseItem(viewCount: Long): ArticleSummaryInfiniteScrollReadResponse.Article {
        return ArticleSummaryInfiniteScrollReadResponse.Article(
            articleId = this.articleId.toString(),
            title = this.title,
            board = ArticleSummaryInfiniteScrollReadResponse.Article.Board(
                boardId = this.board.boardId.toString(),
                name = this.board.name,
                slug = this.board.slug,
            ),
            articleCategory = ArticleSummaryInfiniteScrollReadResponse.Article.ArticleCategory(
                articleCategoryId = this.articleCategory.articleCategoryId.toString(),
                name = this.board.name,
                slug = this.board.slug,
            ),
            writer = ArticleSummaryInfiniteScrollReadResponse.Article.Writer(
                writerId = this.writer.writerId.toString(),
                nickname = this.writer.nickname,
            ),
            viewCount = viewCount,
            commentCount = this.commentCount,
            likeCount = this.likeCount,
            dislikeCount = this.dislikeCount,
            createdAt = this.createdAt.toZonedDateTime(),
        )
    }
}
