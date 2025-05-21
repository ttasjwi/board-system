package com.ttasjwi.board.system.articlelike.domain

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleLikeCountPersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleLikePersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-application-service] ArticleLikeCancelUseCase 테스트")
class ArticleLikeCancelUseCaseImplTest {

    private lateinit var articleLikeCancelUseCase: ArticleLikeCancelUseCase
    private lateinit var currentTime: AppDateTime
    private lateinit var authUser: AuthUser
    private lateinit var articleLikeCountPersistencePortFixture: ArticleLikeCountPersistencePortFixture
    private lateinit var articleLikePersistencePortFixture: ArticleLikePersistencePortFixture
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleLikeCancelUseCase = container.articleLikeCancelUseCase

        currentTime = appDateTimeFixture(minute = 8)
        container.timeManagerFixture.changeCurrentTime(currentTime)

        authUser = authUserFixture(userId = 13L, role = Role.USER)
        container.authUserLoaderFixture.changeAuthUser(authUser)

        articleLikeCountPersistencePortFixture = container.articleLikeCountPersistencePortFixture
        articleLikePersistencePortFixture = container.articleLikePersistencePortFixture
        articlePersistencePortFixture = container.articlePersistencePortFixture
    }


    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture(
                articleId = 1333L,
                articleCategoryId = 5L,
            )
        )
        articleLikePersistencePortFixture.save(
            articleLikeFixture(
                articleLikeId = 12314135L,
                articleId = article.articleId,
                userId = authUser.userId,
                createdAt = appDateTimeFixture(minute = 8)
            )
        )

        articleLikeCountPersistencePortFixture.increase(article.articleId)

        // when
        val response = articleLikeCancelUseCase.cancelLike(article.articleId)

        // then
        assertThat(response.articleId).isEqualTo(article.articleId.toString())
        assertThat(response.userId).isEqualTo(authUser.userId.toString())
        assertThat(response.canceledAt).isEqualTo(currentTime.toZonedDateTime())
    }
}
