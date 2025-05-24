package com.ttasjwi.board.system.articleview.domain

import com.ttasjwi.board.system.articleview.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articleview.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articleview.domain.port.fixture.ArticleViewCountPersistencePortFixture
import com.ttasjwi.board.system.articleview.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-view-application-service] ArticleViewCountReadUseCaseImpl 테스트")
class ArticleViewCountReadUseCaseImplTest {

    private lateinit var articleViewCountReadUseCase: ArticleViewCountReadUseCase
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture
    private lateinit var articleViewCountPersistencePortFixture: ArticleViewCountPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleViewCountReadUseCase = container.articleViewCountReadUseCase
        articlePersistencePortFixture = container.articlePersistencePortFixture
        articleViewCountPersistencePortFixture = container.articleViewCountPersistencePortFixture
    }


    @Test
    @DisplayName("성공 - 조회수가 있을 경우")
    fun testSuccess1() {
        // given
        val articleId = 13L
        articlePersistencePortFixture.save(articleId)

        articleViewCountPersistencePortFixture.increase(articleId)
        articleViewCountPersistencePortFixture.increase(articleId)
        articleViewCountPersistencePortFixture.increase(articleId)
        articleViewCountPersistencePortFixture.increase(articleId)

        // when
        val response = articleViewCountReadUseCase.readViewCount(articleId)

        // then
        assertThat(response.articleId).isEqualTo(articleId.toString())
        assertThat(response.viewCount).isEqualTo(4L)
    }

    @Test
    @DisplayName("성공 - 조회수가 저장되어 있지 않은 경우 조회수는 0이다.")
    fun testSuccess2() {
        // given
        val articleId = 13L
        articlePersistencePortFixture.save(articleId)

        // when
        val response = articleViewCountReadUseCase.readViewCount(articleId)

        // then
        assertThat(response.articleId).isEqualTo(articleId.toString())
        assertThat(response.viewCount).isEqualTo(0L)
    }


    @Test
    @DisplayName("게시글이 저장되어 있지 않은 경우 예외 발생")
    fun testArticleNotFound() {
        // given
        val articleId = 14443L

        // when
        val exception = assertThrows<ArticleNotFoundException> {
            articleViewCountReadUseCase.readViewCount(articleId)
        }

        // then
        assertThat(exception.args).containsExactly("articleId", articleId)
    }
}
