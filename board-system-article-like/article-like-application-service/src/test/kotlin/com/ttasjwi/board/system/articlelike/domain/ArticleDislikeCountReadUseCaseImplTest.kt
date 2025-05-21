package com.ttasjwi.board.system.articlelike.domain

import com.ttasjwi.board.system.articlelike.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleDislikeCountPersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-like-application-service] ArticleDislikeCountReadUseCase 테스트")
class ArticleDislikeCountReadUseCaseImplTest {

    private lateinit var articleDislikeCountReadUseCase: ArticleDislikeCountReadUseCase
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture
    private lateinit var articleDislikeCountPersistencePortFixture: ArticleDislikeCountPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleDislikeCountReadUseCase = container.articleDislikeCountReadUseCase
        articlePersistencePortFixture = container.articlePersistencePortFixture
        articleDislikeCountPersistencePortFixture = container.articleDislikeCountPersistencePortFixture
    }

    @Test
    @DisplayName("성공 테스트 - 게시글 싫어요 수가 있을 때")
    fun testSuccess1() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 134151235L,
            )
        )
        articleDislikeCountPersistencePortFixture.increase(article.articleId)

        // when
        val response = articleDislikeCountReadUseCase.readDislikeCount(article.articleId)

        // then
        assertThat(response.articleId).isEqualTo(article.articleId.toString())
        assertThat(response.dislikeCount).isEqualTo(1L)
    }

    @Test
    @DisplayName("성공 테스트 - 게시글 싫어요 수가 없을 때")
    fun testSuccess2() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 134151235L,
            )
        )

        // when
        val response = articleDislikeCountReadUseCase.readDislikeCount(article.articleId)

        // then
        assertThat(response.articleId).isEqualTo(article.articleId.toString())
        assertThat(response.dislikeCount).isEqualTo(0L)
    }

    @Test
    @DisplayName("게시글이 존재하지 않으면 예외 발생")
    fun testArticleNotFound() {
        // given
        val articleId = 1235135L

        // when
        val exception = assertThrows<ArticleNotFoundException> {
            articleDislikeCountReadUseCase.readDislikeCount(articleId)
        }

        // then
        assertThat(exception.args).containsExactly("articleId", articleId)
    }
}
