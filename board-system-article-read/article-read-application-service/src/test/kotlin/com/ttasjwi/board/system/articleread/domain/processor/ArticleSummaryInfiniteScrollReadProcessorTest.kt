package com.ttasjwi.board.system.articleread.domain.processor

import com.ttasjwi.board.system.articleread.domain.dto.ArticleSummaryInfiniteScrollReadQuery
import com.ttasjwi.board.system.articleread.domain.model.fixture.articleSummaryQueryModelFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.ArticleSummaryStorageFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.ArticleViewCountStorageFixture
import com.ttasjwi.board.system.articleread.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-read-application-service] ArticleSummaryInfiniteScrollReadProcessor 테스트")
class ArticleSummaryInfiniteScrollReadProcessorTest {

    private lateinit var processor: ArticleSummaryInfiniteScrollReadProcessor
    private lateinit var articleSummaryStorageFixture: ArticleSummaryStorageFixture
    private lateinit var articleViewCountStorageFixture: ArticleViewCountStorageFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        processor = container.articleSummaryInfiniteScrollReadProcessor
        articleSummaryStorageFixture = container.articleSummaryStorageFixture
        articleViewCountStorageFixture = container.articleViewCountStorageFixture
    }

    @Test
    @DisplayName("성공 - 다음 페이지가 있는 경우")
    fun test1() {
        // given
        val boardId = 1234566L
        for (id in 1L..10L) {
            val articleSummary = articleSummaryQueryModelFixture(
                articleId = id,
                boardId = boardId,
                title = "title-$id",
            )
            articleSummaryStorageFixture.save(articleSummary)
            articleViewCountStorageFixture.save(
                articleId = id,
                viewCount = id
            )
        }

        val query = ArticleSummaryInfiniteScrollReadQuery(
            boardId = boardId,
            pageSize = 3L,
            lastArticleId = 8L
        )

        // when
        val response = processor.readAllInfiniteScroll(query)

        // then
        val articleIds = response.articles.map { it.articleId }
        val viewCounts = response.articles.map { it.viewCount }

        assertThat(response.hasNext).isTrue()
        assertThat(response.articles).hasSize(query.pageSize.toInt())

        // 10 9 8 [7 6 5] 4 3 2 ...
        assertThat(articleIds).containsExactly("7", "6", "5")
        assertThat(viewCounts).containsExactly(7, 6, 5)
    }

    @Test
    @DisplayName("성공 - 다음 페이지가 없는 경우")
    fun test2() {
        // given
        val boardId = 1234566L
        for (id in 1L..10L) {
            val articleSummary = articleSummaryQueryModelFixture(
                articleId = id,
                boardId = boardId,
                title = "title-$id",
            )
            articleSummaryStorageFixture.save(articleSummary)
            articleViewCountStorageFixture.save(
                articleId = id,
                viewCount = id
            )
        }

        val query = ArticleSummaryInfiniteScrollReadQuery(
            boardId = boardId,
            pageSize = 3L,
            lastArticleId = 3L
        )

        // when
        val response = processor.readAllInfiniteScroll(query)

        // then
        val articleIds = response.articles.map { it.articleId }
        val viewCounts = response.articles.map { it.viewCount }

        assertThat(response.hasNext).isFalse()
        assertThat(response.articles).hasSize(2)

        // 10 9 8 7 6 5 4 3 [2 1]
        assertThat(articleIds).containsExactly("2", "1")
        assertThat(viewCounts).containsExactly(2L, 1L)
    }
}
