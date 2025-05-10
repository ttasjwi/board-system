package com.ttasjwi.board.system.article.domain.processor

import com.ttasjwi.board.system.article.domain.dto.ArticleInfiniteScrollReadQuery
import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.article.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-application-service] ArticleInfiniteScrollReadProcessor 테스트")
class ArticleInfiniteScrollReadProcessorTest {

    private lateinit var processor: ArticleInfiniteScrollReadProcessor
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        processor = container.articleInfiniteScrollReadProcessor
        articlePersistencePortFixture = container.articlePersistencePortFixture
    }

    @Test
    @DisplayName("성공 - 다음 페이지가 있는 경우")
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


        val query = ArticleInfiniteScrollReadQuery(
            boardId = boardId,
            pageSize = 3L,
            lastArticleId = 8L
        )

        // when
        val response = processor.readAllInfiniteScroll(query)

        // then
        val articleIds = response.articles.map { it.articleId }
        assertThat(response.hasNext).isTrue()
        assertThat(response.articles).hasSize(query.pageSize.toInt())

        // 10 9 8 [7 6 5] 4 3 2 ...
        assertThat(articleIds).containsExactly("7", "6", "5")
    }

    @Test
    @DisplayName("성공 - 다음 페이지가 없는 경우")
    fun test2() {
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


        val query = ArticleInfiniteScrollReadQuery(
            boardId = boardId,
            pageSize = 3L,
            lastArticleId = 3L
        )

        // when
        val response = processor.readAllInfiniteScroll(query)

        // then
        val articleIds = response.articles.map { it.articleId }
        assertThat(response.hasNext).isFalse()
        assertThat(response.articles).hasSize(2)

        // 10 9 8 7 6 5 4 3 [2 1]
        assertThat(articleIds).containsExactly("2", "1")
    }
}
