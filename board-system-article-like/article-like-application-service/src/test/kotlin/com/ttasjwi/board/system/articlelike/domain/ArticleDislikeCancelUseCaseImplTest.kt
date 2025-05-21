package com.ttasjwi.board.system.articlelike.domain

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleDislikeFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleDislikeCountPersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleDislikePersistencePortFixture
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

@DisplayName("[article-like-application-service] ArticleDislikeCancelUseCase 테스트")
class ArticleDislikeCancelUseCaseImplTest {

    private lateinit var articleDislikeCancelUseCase: ArticleDislikeCancelUseCase
    private lateinit var currentTime: AppDateTime
    private lateinit var authUser: AuthUser
    private lateinit var articleDislikeCountPersistencePortFixture: ArticleDislikeCountPersistencePortFixture
    private lateinit var articleDislikePersistencePortFixture: ArticleDislikePersistencePortFixture
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleDislikeCancelUseCase = container.articleDislikeCancelUseCase

        currentTime = appDateTimeFixture(minute = 8)
        container.timeManagerFixture.changeCurrentTime(currentTime)

        authUser = authUserFixture(userId = 13L, role = Role.USER)
        container.authUserLoaderFixture.changeAuthUser(authUser)

        articleDislikeCountPersistencePortFixture = container.articleDislikeCountPersistencePortFixture
        articleDislikePersistencePortFixture = container.articleDislikePersistencePortFixture
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
        articleDislikePersistencePortFixture.save(
            articleDislikeFixture(
                articleDislikeId = 12314135L,
                articleId = article.articleId,
                userId = authUser.userId,
                createdAt = appDateTimeFixture(minute = 8)
            )
        )

        articleDislikeCountPersistencePortFixture.increase(article.articleId)

        // when
        val response = articleDislikeCancelUseCase.cancelDislike(article.articleId)

        // then
        assertThat(response.articleId).isEqualTo(article.articleId.toString())
        assertThat(response.userId).isEqualTo(authUser.userId.toString())
        assertThat(response.canceledAt).isEqualTo(currentTime.toZonedDateTime())
    }
}
