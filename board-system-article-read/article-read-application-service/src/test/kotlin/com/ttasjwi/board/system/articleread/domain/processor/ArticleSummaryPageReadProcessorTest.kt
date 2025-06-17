package com.ttasjwi.board.system.articleread.domain.processor

import com.ttasjwi.board.system.articleread.domain.dto.ArticleSummaryPageReadQuery
import com.ttasjwi.board.system.articleread.domain.model.fixture.articleSummaryQueryModelFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.ArticleSummaryStorageFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.ArticleViewCountStorageFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.BoardArticleCountStorageFixture
import com.ttasjwi.board.system.articleread.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@DisplayName("[article-read-application-service] ArticleSummaryPageReadProcessor 테스트")
class ArticleSummaryPageReadProcessorTest {

    private lateinit var articleSummaryPageReadProcessor: ArticleSummaryPageReadProcessor
    private lateinit var articleSummaryStorageFixture: ArticleSummaryStorageFixture
    private lateinit var articleViewCountStorageFixture: ArticleViewCountStorageFixture
    private lateinit var boardArticleCountStorageFixture: BoardArticleCountStorageFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleSummaryPageReadProcessor = container.articleSummaryPageReadProcessor
        articleSummaryStorageFixture = container.articleSummaryStorageFixture
        articleViewCountStorageFixture = container.articleViewCountStorageFixture
        boardArticleCountStorageFixture = container.boardArticleCountStorageFixture
    }

    @Test
    @DisplayName("성공테스트 - 조회")
    fun testSuccess() {
        // given
        val boardId = 1234566L
        val articleCount = 50L

        for (id in 1L..articleCount) {
            val articleSummary = articleSummaryQueryModelFixture(
                boardId = boardId,
                articleId = id,
                title = "title-$id",
            )
            articleSummaryStorageFixture.save(articleSummary)
            articleViewCountStorageFixture.save(
                articleId = id,
                viewCount = id
            )
        }
        boardArticleCountStorageFixture.save(boardId, articleCount)

        val query = ArticleSummaryPageReadQuery(
            boardId = boardId,
            page = 2L,
            pageSize = 3L
        )

        // when
        val response = articleSummaryPageReadProcessor.readAllPage(query)
        val articleIds = response.articles.map { it.articleId }
        val viewCounts = response.articles.map { it.viewCount }

        assertThat(response.page).isEqualTo(query.page)
        assertThat(response.pageSize).isEqualTo(query.pageSize)
        assertThat(response.pageGroupSize).isEqualTo(ArticleSummaryPageReadProcessor.ARTICLE_PAGE_GROUP_SIZE)
        assertThat(response.pageGroupStart).isEqualTo(1)
        assertThat(response.pageGroupEnd).isEqualTo(10L)
        assertThat(response.hasNextPage).isTrue()
        assertThat(response.hasNextPageGroup).isTrue()
        assertThat(response.articles).hasSize(query.pageSize.toInt())

        // [50 49 48] -> [47 46 45]
        assertThat(articleIds).containsExactly("47", "46", "45")
        assertThat(viewCounts).containsExactly(47, 46, 45)
    }
}
