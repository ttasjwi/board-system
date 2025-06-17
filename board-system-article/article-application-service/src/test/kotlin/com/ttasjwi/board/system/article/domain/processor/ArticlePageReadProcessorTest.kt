package com.ttasjwi.board.system.article.domain.processor

import com.ttasjwi.board.system.article.domain.dto.ArticlePageReadQuery
import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.article.domain.port.fixture.BoardArticleCountPersistencePortFixture
import com.ttasjwi.board.system.article.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@DisplayName("[article-application-service] ArticlePageReadProcessor 테스트")
class ArticlePageReadProcessorTest {

    private lateinit var articlePageReadProcessor: ArticlePageReadProcessor
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture
    private lateinit var boardArticleCountPersistencePortFixture: BoardArticleCountPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articlePageReadProcessor = container.articlePageReadProcessor
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

        val query = ArticlePageReadQuery(
            boardId = boardId,
            page = 2L,
            pageSize = 3L
        )

        // when
        val response = articlePageReadProcessor.readAllPage(query)
        val articleIds = response.articles.map { it.articleId }

        assertThat(response.page).isEqualTo(query.page)
        assertThat(response.pageSize).isEqualTo(query.pageSize)
        assertThat(response.pageGroupSize).isEqualTo(ArticlePageReadProcessor.ARTICLE_PAGE_GROUP_SIZE)
        assertThat(response.pageGroupStart).isEqualTo(1)
        assertThat(response.pageGroupEnd).isEqualTo(10L)
        assertThat(response.hasNextPage).isTrue()
        assertThat(response.hasNextPageGroup).isTrue()
        assertThat(response.articles).hasSize(query.pageSize.toInt())

        // [50 49 48] -> [47 46 45]
        assertThat(articleIds).containsExactly("47", "46", "45")
    }

    @Test
    @DisplayName("게시글이 존재하지 않는 경우 테스트")
    fun testNoArticles() {
        // given
        val boardId = 1234566L

        val query = ArticlePageReadQuery(
            boardId = boardId,
            page = 2L,
            pageSize = 3L
        )

        // when
        val response = articlePageReadProcessor.readAllPage(query)
        assertThat(response.page).isEqualTo(query.page)
        assertThat(response.pageSize).isEqualTo(query.pageSize)
        assertThat(response.pageGroupSize).isEqualTo(ArticlePageReadProcessor.ARTICLE_PAGE_GROUP_SIZE)
        assertThat(response.pageGroupStart).isEqualTo(1)
        assertThat(response.pageGroupEnd).isEqualTo(0)
        assertThat(response.hasNextPage).isFalse()
        assertThat(response.hasNextPageGroup).isFalse()
        assertThat(response.articles).hasSize(0)
    }

}
