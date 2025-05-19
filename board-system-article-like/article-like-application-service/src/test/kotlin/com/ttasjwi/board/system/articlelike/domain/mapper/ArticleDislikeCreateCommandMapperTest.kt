package com.ttasjwi.board.system.articlelike.domain.mapper

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

@DisplayName("[article-like-application-service] ArticleDislikeCreateCommandMapper 테스트")
class ArticleDislikeCreateCommandMapperTest {

    private lateinit var articleDislikeCreateCommandMapper: ArticleDislikeCreateCommandMapper
    private lateinit var currentTime: AppDateTime
    private lateinit var authUser: AuthUser

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleDislikeCreateCommandMapper = container.articleDislikeCreateCommandMapper

        currentTime = appDateTimeFixture(minute = 8)
        container.timeManagerFixture.changeCurrentTime(currentTime)

        authUser = authUserFixture(userId = 13L, role = Role.USER)
        container.authUserLoaderFixture.changeAuthUser(authUser)
    }


    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val articleId = 5L

        // when
        val command = articleDislikeCreateCommandMapper.mapToCommand(articleId)

        // then
        assertThat(command.articleId).isEqualTo(articleId)
        assertThat(command.dislikeUser).isEqualTo(authUser)
        assertThat(command.currentTime).isEqualTo(currentTime)
    }

}
