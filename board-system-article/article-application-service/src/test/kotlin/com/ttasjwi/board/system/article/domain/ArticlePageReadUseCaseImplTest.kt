package com.ttasjwi.board.system.article.domain

import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.article.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-application-service] ArticlePageReadUseCase 테스트")
class ArticlePageReadUseCaseImplTest {

    private lateinit var articlePageReadUseCase: ArticlePageReadUseCase
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        articlePageReadUseCase = container.articlePageReadUseCase
        articlePersistencePortFixture = container.articlePersistencePortFixture
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
        }

        val request = ArticlePageReadRequest(
            boardId = boardId,
            page = 2L,
            pageSize = 3L
        )

        // when
        val response = articlePageReadUseCase.readAllPage(request)
        val articleIds = response.articles.map { it.articleId }

        // ------- 10페이지 단위 limit 을 적용하므로, 최대 3 * 10 + 1 건까지 count를 세야함.
        assertThat(response.articleCount).isEqualTo(31)
        assertThat(response.articles).hasSize(request.pageSize!!.toInt())

        // [50 49 48] -> [47 46 45]
        assertThat(articleIds).containsExactly("47", "46", "45")
    }
}
