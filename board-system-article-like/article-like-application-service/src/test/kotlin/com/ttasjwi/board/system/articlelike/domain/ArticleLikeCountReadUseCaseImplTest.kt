package com.ttasjwi.board.system.articlelike.domain

import com.ttasjwi.board.system.articlelike.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeCountFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleLikeCountPersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-like-application-service] ArticleLikeCountReadUseCase 테스트")
class ArticleLikeCountReadUseCaseImplTest {

    private lateinit var articleLikeCountReadUseCase: ArticleLikeCountReadUseCase
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture
    private lateinit var articleLikeCountPersistencePortFixture: ArticleLikeCountPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleLikeCountReadUseCase = container.articleLikeCountReadUseCase
        articlePersistencePortFixture = container.articlePersistencePortFixture
        articleLikeCountPersistencePortFixture = container.articleLikeCountPersistencePortFixture
    }

    @Test
    @DisplayName("성공 테스트 - 게시글 좋아요수가 있을 때")
    fun testSuccess1() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 134151235L,
            )
        )
        articleLikeCountPersistencePortFixture.increase(article.articleId)

        // when
        val response = articleLikeCountReadUseCase.readLikeCount(article.articleId)

        // then
        assertThat(response.articleId).isEqualTo(article.articleId.toString())
        assertThat(response.likeCount).isEqualTo(1L)
    }

    @Test
    @DisplayName("성공 테스트 - 게시글 좋아요수가 없을 때")
    fun testSuccess2() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 134151235L,
            )
        )

        // when
        val response = articleLikeCountReadUseCase.readLikeCount(article.articleId)

        // then
        assertThat(response.articleId).isEqualTo(article.articleId.toString())
        assertThat(response.likeCount).isEqualTo(0L)
    }

    @Test
    @DisplayName("게시글이 존재하지 않으면 예외 발생")
    fun testArticleNotFound() {
        // given
        val articleId = 1235135L

        // when
        val exception = assertThrows<ArticleNotFoundException> {
            articleLikeCountReadUseCase.readLikeCount(articleId)
        }

        // then
        assertThat(exception.args).containsExactly("articleId", articleId)
    }
}
