package com.ttasjwi.board.system.article.domain

import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.article.domain.port.fixture.BoardArticleCountPersistencePortFixture
import com.ttasjwi.board.system.article.domain.processor.ArticlePageReadProcessor
import com.ttasjwi.board.system.article.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-application-service] ArticlePageReadUseCase 테스트")
class ArticlePageReadUseCaseImplTest {

    private lateinit var articlePageReadUseCase: ArticlePageReadUseCase
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture
    private lateinit var boardArticleCountPersistencePortFixture: BoardArticleCountPersistencePortFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        articlePageReadUseCase = container.articlePageReadUseCase
        articlePersistencePortFixture = container.articlePersistencePortFixture
        boardArticleCountPersistencePortFixture = container.boardArticleCountPersistencePortFixture
    }
    @Test
    @DisplayName("성공테스트")
    fun testSuccess() {
        // given
        val boardId = 1234566L
        for (i in 1..50) {
            val article = articleFixture(
                articleId = i.toLong(),
                boardId = boardId,
                title = "title-$i",
            )
            articlePersistencePortFixture.save(article)
            boardArticleCountPersistencePortFixture.increase(boardId)
        }

        val request = ArticlePageReadRequest(
            boardId = boardId,
            page = 2L,
            pageSize = 3L
        )

        // when
        val response = articlePageReadUseCase.readAllPage(request)
        val articleIds = response.articles.map { it.articleId }

        assertThat(response.page).isEqualTo(request.page)
        assertThat(response.pageSize).isEqualTo(request.pageSize)
        assertThat(response.pageGroupSize).isEqualTo(ArticlePageReadProcessor.ARTICLE_PAGE_GROUP_SIZE)
        assertThat(response.pageGroupStart).isEqualTo(1)
        assertThat(response.pageGroupEnd).isEqualTo(10L)
        assertThat(response.hasNextPage).isTrue()
        assertThat(response.hasNextPageGroup).isTrue()
        assertThat(response.articles).hasSize(request.pageSize!!.toInt())

        // [50 49 48] -> [47 46 45]
        assertThat(articleIds).containsExactly("47", "46", "45")
    }
}
