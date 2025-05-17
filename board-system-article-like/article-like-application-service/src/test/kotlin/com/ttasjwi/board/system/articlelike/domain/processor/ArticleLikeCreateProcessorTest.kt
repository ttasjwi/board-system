package com.ttasjwi.board.system.articlelike.domain.processor

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleLikeCreateCommand
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleAlreadyLikedException
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleLikeNotAllowedException
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleCategoryFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeCountFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleCategoryPersistencePortFixture
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

@DisplayName("[article-like-application-service] ArticleLikeCreateProcessor 테스트")
class ArticleLikeCreateProcessorTest {

    private lateinit var articleLikeCreateProcessor: ArticleLikeCreateProcessor
    private lateinit var articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture
    private lateinit var articleLikeCountPersistencePortFixture: ArticleLikeCountPersistencePortFixture
    private lateinit var articleLikePersistencePortFixture: ArticleLikePersistencePortFixture
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        articleLikeCreateProcessor = container.articleLikeCreateProcessor

        articleCategoryPersistencePortFixture = container.articleCategoryPersistencePortFixture
        articleLikeCountPersistencePortFixture = container.articleLikeCountPersistencePortFixture
        articleLikePersistencePortFixture = container.articleLikePersistencePortFixture
        articlePersistencePortFixture = container.articlePersistecnePortFixture
    }


    @Test
    @DisplayName("성공 - 최초의 좋아요")
    fun testSuccess1() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 1333L,
                articleCategoryId = 5L,
            )
        )

        articleCategoryPersistencePortFixture.save(
            articleCategoryFixture(
                articleCategoryId = article.articleCategoryId,
                allowLike = true,
            )
        )

        val command = ArticleLikeCreateCommand(
            articleId = article.articleId,
            likeUser = authUserFixture(userId = 3L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val articleLike = articleLikeCreateProcessor.like(command)

        // then
        val findArticleLike = articleLikePersistencePortFixture.findByIdOrNullTest(articleLike.articleLikeId)!!
        val articleLikeCount = articleLikeCountPersistencePortFixture.findByIdOrNull(command.articleId)!!

        assertThat(articleLike.articleLikeId).isNotNull()
        assertThat(articleLike.articleId).isEqualTo(command.articleId)
        assertThat(articleLike.userId).isEqualTo(command.likeUser.userId)
        assertThat(articleLike.createdAt).isEqualTo(command.currentTime)
        assertThat(findArticleLike.articleLikeId).isEqualTo(articleLike.articleLikeId)
        assertThat(findArticleLike.articleId).isEqualTo(articleLike.articleId)
        assertThat(findArticleLike.userId).isEqualTo(articleLike.userId)
        assertThat(findArticleLike.createdAt).isEqualTo(articleLike.createdAt)
        assertThat(articleLikeCount.articleId).isEqualTo(command.articleId)
        assertThat(articleLikeCount.likeCount).isEqualTo(1)
    }


    @Test
    @DisplayName("성공 - 두번째 이후의 좋아요")
    fun testSuccess2() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 1333L,
                articleCategoryId = 5L,
            )
        )

        articleCategoryPersistencePortFixture.save(
            articleCategoryFixture(
                articleCategoryId = article.articleCategoryId,
                allowLike = true,
            )
        )

        articleLikePersistencePortFixture.save(
            articleLikeFixture(
                articleLikeId = 123L,
                articleId = article.articleId,
                userId = 55L,
                createdAt = appDateTimeFixture(minute = 33)
            )
        )

        articleLikeCountPersistencePortFixture.save(
            articleLikeCount = articleLikeCountFixture(
                articleId = article.articleId,
                likeCount = 1L
            )
        )

        val command = ArticleLikeCreateCommand(
            articleId = article.articleId,
            likeUser = authUserFixture(userId = 3L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val articleLike = articleLikeCreateProcessor.like(command)

        // then
        val findArticleLike = articleLikePersistencePortFixture.findByIdOrNullTest(articleLike.articleLikeId)!!
        val articleLikeCount = articleLikeCountPersistencePortFixture.findByIdOrNull(command.articleId)!!

        assertThat(articleLike.articleLikeId).isNotNull()
        assertThat(articleLike.articleId).isEqualTo(command.articleId)
        assertThat(articleLike.userId).isEqualTo(command.likeUser.userId)
        assertThat(articleLike.createdAt).isEqualTo(command.currentTime)
        assertThat(findArticleLike.articleLikeId).isEqualTo(articleLike.articleLikeId)
        assertThat(findArticleLike.articleId).isEqualTo(articleLike.articleId)
        assertThat(findArticleLike.userId).isEqualTo(articleLike.userId)
        assertThat(findArticleLike.createdAt).isEqualTo(articleLike.createdAt)
        assertThat(articleLikeCount.articleId).isEqualTo(command.articleId)
        assertThat(articleLikeCount.likeCount).isEqualTo(2)
    }


    @Test
    @DisplayName("게시글 조회 실패하면 예외")
    fun testArticleNotFound() {
        // given
        val command = ArticleLikeCreateCommand(
            articleId = 333L,
            likeUser = authUserFixture(userId = 3L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<ArticleNotFoundException> {
            articleLikeCreateProcessor.like(command)
        }

        // then
        assertThat(exception.args).containsExactly("articleId", command.articleId)
    }


    @Test
    @DisplayName("게시글 카테고리 조회 실패하면 예외")
    fun testArticleCategoryNotFound() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 1333L,
                articleCategoryId = 5L,
            )
        )

        val command = ArticleLikeCreateCommand(
            articleId = article.articleId,
            likeUser = authUserFixture(userId = 3L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<IllegalStateException> {
            articleLikeCreateProcessor.like(command)
        }

        // then
        assertThat(exception.message).isEqualTo("게시글 카테고리가 존재하지 않음 (articleCategoryId=${article.articleCategoryId})")
    }


    @Test
    @DisplayName("게시글 좋아요가 허용되지 않을 경우 예외")
    fun testArticleLikeNotAllowed() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 1333L,
                articleCategoryId = 5L,
            )
        )

        articleCategoryPersistencePortFixture.save(
            articleCategoryFixture(
                articleCategoryId = article.articleCategoryId,
                allowLike = false,
            )
        )
        val command = ArticleLikeCreateCommand(
            articleId = article.articleId,
            likeUser = authUserFixture(userId = 3L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<ArticleLikeNotAllowedException> {
            articleLikeCreateProcessor.like(command)
        }

        // then
        assertThat(exception.args).containsExactly(command.articleId, article.articleCategoryId)
    }


    @Test
    @DisplayName("같은 사용자가 다시 좋아요 시도하면 예외")
    fun testDuplicateLike() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 1333L,
                articleCategoryId = 5L,
            )
        )

        articleCategoryPersistencePortFixture.save(
            articleCategoryFixture(
                articleCategoryId = article.articleCategoryId,
                allowLike = true,
            )
        )

        val likeUser = authUserFixture(userId = 3L, role = Role.USER)

        articleLikePersistencePortFixture.save(
            articleLikeFixture(
                articleLikeId = 123L,
                articleId = article.articleId,
                userId = likeUser.userId,
                createdAt = appDateTimeFixture(minute = 33)
            )
        )

        articleLikeCountPersistencePortFixture.save(
            articleLikeCount = articleLikeCountFixture(
                articleId = article.articleId,
                likeCount = 1L
            )
        )

        val command = ArticleLikeCreateCommand(
            articleId = article.articleId,
            likeUser = likeUser,
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<ArticleAlreadyLikedException> {
            articleLikeCreateProcessor.like(command)
        }

        assertThat(exception.args).containsExactly(command.articleId, likeUser.userId)
    }
}
