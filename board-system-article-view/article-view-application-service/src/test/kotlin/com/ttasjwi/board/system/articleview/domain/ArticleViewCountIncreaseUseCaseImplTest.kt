package com.ttasjwi.board.system.articleview.domain

import com.ttasjwi.board.system.articleview.domain.command.ArticleViewCountIncreaseCommand
import com.ttasjwi.board.system.articleview.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articleview.domain.port.fixture.ArticleViewCountPersistencePortFixture
import com.ttasjwi.board.system.articleview.domain.processor.ArticleViewCountIncreaseProcessor
import com.ttasjwi.board.system.articleview.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-view-application-service] ArticleViewCountIncreaseUseCaseImpl 테스트")
class ArticleViewCountIncreaseUseCaseImplTest {

    private lateinit var articleViewCountIncreaseUseCase: ArticleViewCountIncreaseUseCase
    private lateinit var authUser: AuthUser
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture
    private lateinit var articleViewCountPersistencePortFixture: ArticleViewCountPersistencePortFixture


    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleViewCountIncreaseUseCase = container.articleViewCountIncreaseUseCase

        authUser = authUserFixture(userId = 3336L, role = Role.USER)
        container.authUserLoaderFixture.changeAuthUser(authUser)

        articlePersistencePortFixture = container.articlePersistencePortFixture
        articleViewCountPersistencePortFixture = container.articleViewCountPersistencePortFixture
    }

    @Test
    @DisplayName("성공테스트 - 락 획득에 성공한 경우")
    fun testSuccess() {
        // given
        val articleId = 13L
        articlePersistencePortFixture.save(articleId)
        articleViewCountPersistencePortFixture.increase(articleId)

        // when
        articleViewCountIncreaseUseCase.increase(articleId)

        // then
        val articleViewCount = articleViewCountPersistencePortFixture.findByIdOrNull(articleId)!!
        assertThat(articleViewCount.articleId).isEqualTo(articleId)
        assertThat(articleViewCount.viewCount).isEqualTo(2L)
    }

}
