package com.ttasjwi.board.system.articleread.domain

import com.ttasjwi.board.system.articleread.domain.model.fixture.articleSummaryQueryModelFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.ArticleSummaryStorageFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.ArticleViewCountStorageFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.BoardArticleCountStorageFixture
import com.ttasjwi.board.system.articleread.domain.processor.ArticleSummaryPageReadProcessor
import com.ttasjwi.board.system.articleread.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

@DisplayName("[article-read-application-service] ArticleSummaryPageReadUseCase 테스트")
class ArticleSummaryPageReadUseCaseImplTest {

    private lateinit var articleSummaryPageReadUseCase: ArticleSummaryPageReadUseCase
    private lateinit var articleSummaryStorageFixture: ArticleSummaryStorageFixture
    private lateinit var articleViewCountStorageFixture: ArticleViewCountStorageFixture
    private lateinit var boardArticleCountStorageFixture: BoardArticleCountStorageFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        articleSummaryPageReadUseCase = container.articleSummaryPageReadUseCase
        articleSummaryStorageFixture = container.articleSummaryStorageFixture
        articleViewCountStorageFixture = container.articleViewCountStorageFixture
        boardArticleCountStorageFixture = container.boardArticleCountStorageFixture
    }

    @Test
    @DisplayName("성공테스트")
    fun testSuccess() {
        // given
        val boardId = 1234566L
        val articleCount = 50L
        for (id in 1L..articleCount) {
            val article = articleSummaryQueryModelFixture(
                articleId = id,
                boardId = boardId,
                title = "title-$id",
            )
            articleSummaryStorageFixture.save(article)
            articleViewCountStorageFixture.save(
                articleId = id,
                viewCount = id
            )
        }
        boardArticleCountStorageFixture.save(boardId, articleCount)

        val request = ArticleSummaryPageReadRequest(
            boardId = boardId,
            page = 2L,
            pageSize = 3L
        )

        // when
        val response = articleSummaryPageReadUseCase.readAllPage(request)
        val articleIds = response.articles.map { it.articleId }
        val viewCounts = response.articles.map { it.viewCount }

        assertThat(response.page).isEqualTo(request.page)
        assertThat(response.pageSize).isEqualTo(request.pageSize)
        assertThat(response.pageGroupSize).isEqualTo(ArticleSummaryPageReadProcessor.ARTICLE_PAGE_GROUP_SIZE)
        assertThat(response.pageGroupStart).isEqualTo(1)
        assertThat(response.pageGroupEnd).isEqualTo(10L)
        assertThat(response.hasNextPage).isTrue()
        assertThat(response.hasNextPageGroup).isTrue()
        assertThat(response.articles).hasSize(request.pageSize!!.toInt())

        // [50 49 48] -> [47 46 45]
        assertThat(articleIds).containsExactly("47", "46", "45")
        assertThat(viewCounts).containsExactly(47, 46, 45)
    }
}
