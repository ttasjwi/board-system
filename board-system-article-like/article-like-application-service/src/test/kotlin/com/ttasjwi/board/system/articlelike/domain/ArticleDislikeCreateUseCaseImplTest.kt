package com.ttasjwi.board.system.articlelike.domain

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleCategoryFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleCategoryPersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-application-service] ArticleDislikeCreateUseCase 테스트")
class ArticleDislikeCreateUseCaseImplTest {

    private lateinit var articleDislikeCreateUseCase: ArticleDislikeCreateUseCase
    private lateinit var currentTime: AppDateTime
    private lateinit var authUser: AuthUser
    private lateinit var articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleDislikeCreateUseCase = container.articleDislikeCreateUseCase

        currentTime = appDateTimeFixture(minute= 8)
        container.timeManagerFixture.changeCurrentTime(currentTime)

        authUser = authUserFixture(userId = 13L, role = Role.USER)
        container.authUserLoaderFixture.changeAuthUser(authUser)

        articleCategoryPersistencePortFixture = container.articleCategoryPersistencePortFixture
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

        articleCategoryPersistencePortFixture.save(
            articleCategoryFixture(
                articleCategoryId = article.articleCategoryId,
                allowLike = true,
            )
        )

        // when
        val response = articleDislikeCreateUseCase.dislike(article.articleId)

        // then
        assertThat(response.articleDislikeId).isNotNull()
        assertThat(response.articleId).isEqualTo(article.articleId.toString())
        assertThat(response.userId).isEqualTo(authUser.userId.toString())
        assertThat(response.createdAt).isEqualTo(currentTime.toZonedDateTime())
    }
}
