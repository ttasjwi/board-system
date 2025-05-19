package com.ttasjwi.board.system.articlelike.domain.processor

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleDislikeCreateCommand
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleAlreadyDislikedException
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleDislikeNotAllowedException
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleCategoryFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleDislikeCountFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleDislikeFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleCategoryPersistencePortFixture
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

@DisplayName("[article-like-application-service] ArticleDislikeCreateProcessor 테스트")
class ArticleDislikeCreateProcessorTest {

    private lateinit var articleDislikeCreateProcessor: ArticleDislikeCreateProcessor
    private lateinit var articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture
    private lateinit var articleDislikeCountPersistencePortFixture: ArticleDislikeCountPersistencePortFixture
    private lateinit var articleDislikePersistencePortFixture: ArticleDislikePersistencePortFixture
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        articleDislikeCreateProcessor = container.articleDislikeCreateProcessor

        articleCategoryPersistencePortFixture = container.articleCategoryPersistencePortFixture
        articleDislikeCountPersistencePortFixture = container.articleDislikeCountPersistencePortFixture
        articleDislikePersistencePortFixture = container.articleDislikePersistencePortFixture
        articlePersistencePortFixture = container.articlePersistencePortFixture
    }


    @Test
    @DisplayName("성공 - 최초의 싫어요")
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

        val command = ArticleDislikeCreateCommand(
            articleId = article.articleId,
            dislikeUser = authUserFixture(userId = 3L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val articleDislike = articleDislikeCreateProcessor.dislike(command)

        // then
        val findArticleDislike =
            articleDislikePersistencePortFixture.findByIdOrNullTest(articleDislike.articleDislikeId)!!
        val articleDislikeCount = articleDislikeCountPersistencePortFixture.findByIdOrNull(command.articleId)!!

        assertThat(articleDislike.articleDislikeId).isNotNull()
        assertThat(articleDislike.articleId).isEqualTo(command.articleId)
        assertThat(articleDislike.userId).isEqualTo(command.dislikeUser.userId)
        assertThat(articleDislike.createdAt).isEqualTo(command.currentTime)
        assertThat(findArticleDislike.articleDislikeId).isEqualTo(articleDislike.articleDislikeId)
        assertThat(findArticleDislike.articleId).isEqualTo(articleDislike.articleId)
        assertThat(findArticleDislike.userId).isEqualTo(articleDislike.userId)
        assertThat(findArticleDislike.createdAt).isEqualTo(articleDislike.createdAt)
        assertThat(articleDislikeCount.articleId).isEqualTo(command.articleId)
        assertThat(articleDislikeCount.dislikeCount).isEqualTo(1)
    }


    @Test
    @DisplayName("성공 - 두번째 이후의 싫어요")
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

        articleDislikePersistencePortFixture.save(
            articleDislikeFixture(
                articleDislikeId = 123L,
                articleId = article.articleId,
                userId = 55L,
                createdAt = appDateTimeFixture(minute = 33)
            )
        )

        articleDislikeCountPersistencePortFixture.save(
            articleDislikeCount = articleDislikeCountFixture(
                articleId = article.articleId,
                dislikeCount = 1L
            )
        )

        val command = ArticleDislikeCreateCommand(
            articleId = article.articleId,
            dislikeUser = authUserFixture(userId = 3L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val articleDislike = articleDislikeCreateProcessor.dislike(command)

        // then
        val findArticleDislike =
            articleDislikePersistencePortFixture.findByIdOrNullTest(articleDislike.articleDislikeId)!!
        val articleDislikeCount = articleDislikeCountPersistencePortFixture.findByIdOrNull(command.articleId)!!

        assertThat(articleDislike.articleDislikeId).isNotNull()
        assertThat(articleDislike.articleId).isEqualTo(command.articleId)
        assertThat(articleDislike.userId).isEqualTo(command.dislikeUser.userId)
        assertThat(articleDislike.createdAt).isEqualTo(command.currentTime)
        assertThat(findArticleDislike.articleDislikeId).isEqualTo(articleDislike.articleDislikeId)
        assertThat(findArticleDislike.articleId).isEqualTo(articleDislike.articleId)
        assertThat(findArticleDislike.userId).isEqualTo(articleDislike.userId)
        assertThat(findArticleDislike.createdAt).isEqualTo(articleDislike.createdAt)
        assertThat(articleDislikeCount.articleId).isEqualTo(command.articleId)
        assertThat(articleDislikeCount.dislikeCount).isEqualTo(2)
    }


    @Test
    @DisplayName("게시글 조회 실패하면 예외")
    fun testArticleNotFound() {
        // given
        val command = ArticleDislikeCreateCommand(
            articleId = 333L,
            dislikeUser = authUserFixture(userId = 3L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<ArticleNotFoundException> {
            articleDislikeCreateProcessor.dislike(command)
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

        val command = ArticleDislikeCreateCommand(
            articleId = article.articleId,
            dislikeUser = authUserFixture(userId = 3L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<IllegalStateException> {
            articleDislikeCreateProcessor.dislike(command)
        }

        // then
        assertThat(exception.message).isEqualTo("게시글 카테고리가 존재하지 않음 (articleCategoryId=${article.articleCategoryId})")
    }


    @Test
    @DisplayName("게시글 싫어요가 허용되지 않을 경우 예외")
    fun testArticleDislikeNotAllowed() {
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
                allowDislike = false,
            )
        )
        val command = ArticleDislikeCreateCommand(
            articleId = article.articleId,
            dislikeUser = authUserFixture(userId = 3L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<ArticleDislikeNotAllowedException> {
            articleDislikeCreateProcessor.dislike(command)
        }

        // then
        assertThat(exception.args).containsExactly(command.articleId, article.articleCategoryId)
    }


    @Test
    @DisplayName("같은 사용자가 다시 싫어요 시도하면 예외")
    fun testDuplicateDislike() {
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

        val dislikeUser = authUserFixture(userId = 3L, role = Role.USER)

        articleDislikePersistencePortFixture.save(
            articleDislikeFixture(
                articleDislikeId = 123L,
                articleId = article.articleId,
                userId = dislikeUser.userId,
                createdAt = appDateTimeFixture(minute = 33)
            )
        )

        articleDislikeCountPersistencePortFixture.save(
            articleDislikeCount = articleDislikeCountFixture(
                articleId = article.articleId,
                dislikeCount = 1L
            )
        )

        val command = ArticleDislikeCreateCommand(
            articleId = article.articleId,
            dislikeUser = dislikeUser,
            currentTime = appDateTimeFixture(minute = 35)
        )

        // when
        val exception = assertThrows<ArticleAlreadyDislikedException> {
            articleDislikeCreateProcessor.dislike(command)
        }

        assertThat(exception.args).containsExactly(command.articleId, dislikeUser.userId)
    }
}
