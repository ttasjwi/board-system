package com.ttasjwi.board.system.articleread.domain.processor

import com.ttasjwi.board.system.articleread.domain.ArticleSummaryPageReadResponse
import com.ttasjwi.board.system.articleread.domain.dto.ArticleSummaryPageReadQuery
import com.ttasjwi.board.system.articleread.domain.model.ArticleSummaryQueryModel
import com.ttasjwi.board.system.articleread.domain.port.ArticleSummaryStorage
import com.ttasjwi.board.system.articleread.domain.port.ArticleViewCountStorage
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.page.PagingInfo
import com.ttasjwi.board.system.common.page.calculateOffset
import com.ttasjwi.board.system.common.page.calculatePageLimit

@ApplicationProcessor
class ArticleSummaryPageReadProcessor(
    private val articleSummaryStorage: ArticleSummaryStorage,
    private val articleViewCountStorage: ArticleViewCountStorage,
) {

    companion object {
        internal const val ARTICLE_PAGE_GROUP_SIZE = 10L
    }

    fun readAllPage(query: ArticleSummaryPageReadQuery): ArticleSummaryPageReadResponse {
        // 게시글 수 조회
        val articleCount = readArticleCount(query)

        // 게시글 조회
        val articles = readArticles(query)

        // 게시글 별 조회수 조회
        val viewCounts = articleViewCountStorage.readViewCounts(articles.map { it.articleId })

        // 페이징 정보 구성
        val pagingInfo = PagingInfo.from(
            page = query.page,
            pageSize = query.pageSize,
            totalCount = articleCount,
            pageGroupSize = ARTICLE_PAGE_GROUP_SIZE
        )

        // 응답 구성
        return makeResponse(pagingInfo, articles, viewCounts)
    }

    private fun readArticleCount(query: ArticleSummaryPageReadQuery) = articleSummaryStorage.count(
        boardId = query.boardId,
        limit = calculatePageLimit(
            page = query.page,
            pageSize = query.pageSize,
            movablePageCount = ARTICLE_PAGE_GROUP_SIZE
        )
    )

    private fun readArticles(query: ArticleSummaryPageReadQuery): List<ArticleSummaryQueryModel> {
        return articleSummaryStorage.findAllPage(
            boardId = query.boardId,
            offSet = calculateOffset(
                page = query.page,
                pageSize = query.pageSize
            ),
            limit = query.pageSize
        )
    }

    private fun makeResponse(
        pagingInfo: PagingInfo,
        articles: List<ArticleSummaryQueryModel>,
        viewCounts: Map<Long, Long>,
    ): ArticleSummaryPageReadResponse {
        return ArticleSummaryPageReadResponse(
            page = pagingInfo.page,
            pageSize = pagingInfo.pageSize,
            pageGroupSize = pagingInfo.pageGroupSize,
            pageGroupStart = pagingInfo.pageGroupStart,
            pageGroupEnd = pagingInfo.pageGroupEnd,
            hasNextPage = pagingInfo.hasNextPage,
            hasNextPageGroup = pagingInfo.hasNextPageGroup,
            articles = articles.map { it.toArticlePageElement(viewCount = viewCounts[it.articleId]!!) }
        )
    }

    private fun ArticleSummaryQueryModel.toArticlePageElement(viewCount: Long): ArticleSummaryPageReadResponse.Article {
        return ArticleSummaryPageReadResponse.Article(
            articleId = this.articleId.toString(),
            title = this.title,
            board = ArticleSummaryPageReadResponse.Article.Board(
                boardId = this.board.boardId.toString(),
                name = this.board.name,
                slug = this.board.slug,
            ),
            articleCategory = ArticleSummaryPageReadResponse.Article.ArticleCategory(
                articleCategoryId = this.articleCategory.articleCategoryId.toString(),
                name = this.articleCategory.name,
                slug = this.articleCategory.slug,
            ),
            writer = ArticleSummaryPageReadResponse.Article.Writer(
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
