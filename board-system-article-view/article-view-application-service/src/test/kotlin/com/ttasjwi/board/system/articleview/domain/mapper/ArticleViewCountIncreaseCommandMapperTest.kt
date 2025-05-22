package com.ttasjwi.board.system.articleview.domain.mapper

import com.ttasjwi.board.system.articleview.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-view-application-service] ArticleViewCountIncreaseCommandMapper 테스트")
class ArticleViewCountIncreaseCommandMapperTest {

    private lateinit var articleViewCountIncreaseCommandMapper: ArticleViewCountIncreaseCommandMapper
    private lateinit var authUser: AuthUser

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleViewCountIncreaseCommandMapper = container.articleViewCountIncreaseCommandMapper

        authUser = authUserFixture(userId = 3336L, role = Role.USER)
        container.authUserLoaderFixture.changeAuthUser(authUser)
    }

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val articleId = 5552L

        // when
        val command = articleViewCountIncreaseCommandMapper.mapToCommand(articleId)

        // then
        assertThat(command.articleId).isEqualTo(articleId)
        assertThat(command.user).isEqualTo(authUser)
    }
}
