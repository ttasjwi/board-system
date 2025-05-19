package com.ttasjwi.board.system.articlelike.domain.processor

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleLikeCancelCommand
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleLikeNotFoundException
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeCountFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleLikeCountPersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleLikePersistencePortFixture
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

@DisplayName("[article-like-application-service] ArticleLikeCancelProcessor 테스트")
class ArticleLikeCancelProcessorTest {

    private lateinit var articleLikeCancelProcessor: ArticleLikeCancelProcessor
    private lateinit var articleLikeCountPersistencePortFixture: ArticleLikeCountPersistencePortFixture
    private lateinit var articleLikePersistencePortFixture: ArticleLikePersistencePortFixture
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        articleLikeCancelProcessor = container.articleLikeCancelProcessor
        articleLikeCountPersistencePortFixture = container.articleLikeCountPersistencePortFixture
        articleLikePersistencePortFixture = container.articleLikePersistencePortFixture
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

        val articleLike = articleLikePersistencePortFixture.save(
            articleLikeFixture(
                articleLikeId = 12314135L,
                articleId = article.articleId,
                userId = user.userId,
                createdAt = appDateTimeFixture(minute = 8)
            )
        )

        val articleLikeCount = articleLikeCountPersistencePortFixture.save(
            articleLikeCountFixture(
                articleId = article.articleId,
                likeCount = 13L
            )
        )
        val prevLikeCount = articleLikeCount.likeCount

        val command = ArticleLikeCancelCommand(
            articleId = article.articleId,
            user = user,
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        articleLikeCancelProcessor.cancelLike(command)

        // then
        val articleLikeExists = articleLikePersistencePortFixture.existsByArticleIdAndUserId(articleLike.articleId, articleLike.userId)
        val findArticleLikeCount = articleLikeCountPersistencePortFixture.findByIdOrNull(article.articleId)!!

        assertThat(articleLikeExists).isFalse()
        assertThat(findArticleLikeCount.articleId).isEqualTo(command.articleId)
        assertThat(findArticleLikeCount.likeCount).isEqualTo(prevLikeCount - 1L)
    }

    @Test
    @DisplayName("게시글 조회 실패하면 예외")
    fun testArticleNotFound() {
        // given
        val user = authUserFixture(userId = 3L, role = Role.USER)

        val command = ArticleLikeCancelCommand(
            articleId = 155556L,
            user = user,
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<ArticleNotFoundException> {
            articleLikeCancelProcessor.cancelLike(command)
        }

        // then
        assertThat(exception.args).containsExactly("articleId", command.articleId)
    }

    @Test
    @DisplayName("게시글 좋아요가 존재하지 않으면 예외 발생")
    fun testArticleLikeNotFound() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 1333L,
                articleCategoryId = 5L,
            )
        )
        val user = authUserFixture(userId = 3L, role = Role.USER)

        val command = ArticleLikeCancelCommand(
            articleId = article.articleId,
            user = user,
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<ArticleLikeNotFoundException> {
            articleLikeCancelProcessor.cancelLike(command)
        }

        // then
        assertThat(exception.args).containsExactly(command.articleId, command.user.userId)
    }

    @Test
    @DisplayName("게시글 좋아요 수가 존재하지 않으면 예외 발생")
    fun testArticleLikeCountNotFound() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 1333L,
                articleCategoryId = 5L,
            )
        )
        val user = authUserFixture(userId = 3L, role = Role.USER)

        articleLikePersistencePortFixture.save(
            articleLikeFixture(
                articleLikeId = 12314135L,
                articleId = article.articleId,
                userId = user.userId,
                createdAt = appDateTimeFixture(minute = 8)
            )
        )

        val command = ArticleLikeCancelCommand(
            articleId = article.articleId,
            user = user,
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<IllegalStateException> {
            articleLikeCancelProcessor.cancelLike(command)
        }

        // then
        assertThat(exception.message).isEqualTo(
            "게시글 좋아요 수가 저장되어 있지 않음(articleId = ${article.articleId})"
        )
    }
}
