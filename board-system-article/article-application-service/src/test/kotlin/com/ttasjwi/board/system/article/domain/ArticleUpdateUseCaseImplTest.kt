package com.ttasjwi.board.system.article.domain

import com.ttasjwi.board.system.article.domain.model.fixture.articleCategoryFixture
import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticleCategoryPersistencePortFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.article.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-application-service] ArticleUpdateUseCaseImpl 테스트")
class ArticleUpdateUseCaseImplTest {

    private lateinit var useCase: ArticleUpdateUseCase
    private lateinit var authUser: AuthUser
    private lateinit var currentTime: AppDateTime
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture
    private lateinit var articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        useCase = container.articleUpdateUseCase

        currentTime = appDateTimeFixture(minute = 3)
        authUser = authUserFixture(userId = 1234558L, role = Role.USER)

        container.timeManagerFixture.changeCurrentTime(currentTime)
        container.authUserLoaderFixture.changeAuthUser(authUser)

        articlePersistencePortFixture = container.articlePersistencePortFixture
        articleCategoryPersistencePortFixture = container.articleCategoryPersistencePortFixture
    }

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val prevTitle = "title"
        val prevContent = "content"

        val newTitle = "new title"
        val newContent = "new content"

        val article = articlePersistencePortFixture.save(
            articleFixture(
                title = prevTitle,
                content = prevContent,
                writerId = authUser.userId,
                createdAt = appDateTimeFixture(minute = 0),
                modifiedAt = appDateTimeFixture(minute = 0)
            )
        )

        articleCategoryPersistencePortFixture.save(
            articleCategoryFixture(
                articleCategoryId = article.articleCategoryId,
                allowSelfEditDelete = true
            )
        )

        val articleId = article.articleId
        val request = ArticleUpdateRequest(
            title = newTitle,
            content = newContent,
        )

        // when
        val response = useCase.update(articleId, request)

        // then
        val findArticle = articlePersistencePortFixture.findByIdOrNull(article.articleId)!!

        assertThat(response.articleId).isEqualTo(articleId.toString())
        assertThat(response.title).isEqualTo(request.title)
        assertThat(response.content).isEqualTo(request.content)
        assertThat(response.modifiedAt).isEqualTo(currentTime.toZonedDateTime())

        assertThat(findArticle.articleId).isEqualTo(articleId)
        assertThat(findArticle.title).isEqualTo(response.title)
        assertThat(findArticle.content).isEqualTo(response.content)
        assertThat(findArticle.modifiedAt).isEqualTo(currentTime)
    }
}
