package com.ttasjwi.board.system.articlelike.domain.processor

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleDislikeCancelCommand
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleDislikeNotFoundException
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleDislikeCountFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleDislikeFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleDislikeCountPersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleDislikePersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-like-application-service] ArticleDislikeCancelProcessor 테스트")
class ArticleDislikeCancelProcessorTest {

    private lateinit var articleDislikeCancelProcessor: ArticleDislikeCancelProcessor
    private lateinit var articleDislikeCountPersistencePortFixture: ArticleDislikeCountPersistencePortFixture
    private lateinit var articleDislikePersistencePortFixture: ArticleDislikePersistencePortFixture
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        articleDislikeCancelProcessor = container.articleDislikeCancelProcessor
        articleDislikeCountPersistencePortFixture = container.articleDislikeCountPersistencePortFixture
        articleDislikePersistencePortFixture = container.articleDislikePersistencePortFixture
        articlePersistencePortFixture = container.articlePersistencePortFixture
    }


    @Test
    @DisplayName("성공")
    fun testSuccess() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 1333L,
                articleCategoryId = 5L,
            )
        )
        val user = authUserFixture(userId = 3L, role = Role.USER)

        val articleDislike = articleDislikePersistencePortFixture.save(
            articleDislikeFixture(
                articleDislikeId = 12314135L,
                articleId = article.articleId,
                userId = user.userId,
                createdAt = appDateTimeFixture(minute = 8)
            )
        )

        val articleDislikeCount = articleDislikeCountPersistencePortFixture.save(
            articleDislikeCountFixture(
                articleId = article.articleId,
                dislikeCount = 13L
            )
        )
        val prevDislikeCount = articleDislikeCount.dislikeCount

        val command = ArticleDislikeCancelCommand(
            articleId = article.articleId,
            user = user,
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        articleDislikeCancelProcessor.cancelDislike(command)

        // then
        val articleDislikeExists = articleDislikePersistencePortFixture.existsByArticleIdAndUserId(
            articleDislike.articleId,
            articleDislike.userId
        )
        val findArticleDislikeCount = articleDislikeCountPersistencePortFixture.findByIdOrNull(article.articleId)!!

        assertThat(articleDislikeExists).isFalse()
        assertThat(findArticleDislikeCount.articleId).isEqualTo(command.articleId)
        assertThat(findArticleDislikeCount.dislikeCount).isEqualTo(prevDislikeCount - 1L)
    }

    @Test
    @DisplayName("게시글 조회 실패하면 예외")
    fun testArticleNotFound() {
        // given
        val user = authUserFixture(userId = 3L, role = Role.USER)

        val command = ArticleDislikeCancelCommand(
            articleId = 155556L,
            user = user,
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<ArticleNotFoundException> {
            articleDislikeCancelProcessor.cancelDislike(command)
        }

        // then
        assertThat(exception.args).containsExactly("articleId", command.articleId)
    }

    @Test
    @DisplayName("게시글 싫어요가 존재하지 않으면 예외 발생")
    fun testArticleDislikeNotFound() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 1333L,
                articleCategoryId = 5L,
            )
        )
        val user = authUserFixture(userId = 3L, role = Role.USER)

        val command = ArticleDislikeCancelCommand(
            articleId = article.articleId,
            user = user,
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<ArticleDislikeNotFoundException> {
            articleDislikeCancelProcessor.cancelDislike(command)
        }

        // then
        assertThat(exception.args).containsExactly(command.articleId, command.user.userId)
    }

    @Test
    @DisplayName("게시글 싫어요 수가 존재하지 않으면 예외 발생")
    fun testArticleDislikeCountNotFound() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 1333L,
                articleCategoryId = 5L,
            )
        )
        val user = authUserFixture(userId = 3L, role = Role.USER)

        articleDislikePersistencePortFixture.save(
            articleDislikeFixture(
                articleDislikeId = 12314135L,
                articleId = article.articleId,
                userId = user.userId,
                createdAt = appDateTimeFixture(minute = 8)
            )
        )

        val command = ArticleDislikeCancelCommand(
            articleId = article.articleId,
            user = user,
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<IllegalStateException> {
            articleDislikeCancelProcessor.cancelDislike(command)
        }

        // then
        assertThat(exception.message).isEqualTo(
            "게시글 싫어요 수가 저장되어 있지 않음(articleId = ${article.articleId})"
        )
    }
}
