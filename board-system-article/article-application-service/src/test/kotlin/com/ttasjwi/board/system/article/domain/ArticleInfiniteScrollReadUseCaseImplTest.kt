package com.ttasjwi.board.system.article.domain

import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.article.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-application-service] ArticleInfiniteScrollReadUseCase 테스트")
class ArticleInfiniteScrollReadUseCaseImplTest {

    private lateinit var useCase: ArticleInfiniteScrollReadUseCase
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        useCase = container.articleInfiniteScrollReadUseCase
        articlePersistencePortFixture = container.articlePersistencePortFixture
    }

    @Test
    @DisplayName("성공 테스트")
    fun test1() {
        // given
        val boardId = 1234566L
        for (i in 1..10) {
            val article = articleFixture(
                articleId = i.toLong(),
                boardId = boardId,
                title = "title-$i",
            )
            articlePersistencePortFixture.save(article)
        }


        val request = ArticleInfiniteScrollReadRequest(
            boardId = boardId,
            pageSize = 3L,
            lastArticleId = 8L
        )

        // when
        val response = useCase.readAllInfiniteScroll(request)

        // then
        val articleIds = response.articles.map { it.articleId }
        assertThat(response.hasNext).isTrue()
        assertThat(response.articles).hasSize(request.pageSize!!.toInt())

        // 10 9 8 [7 6 5] 4 3 2 ...
        assertThat(articleIds).containsExactly("7", "6", "5")
    }

}
