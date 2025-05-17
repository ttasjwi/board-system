package com.ttasjwi.board.system.articlelike.domain.mapper

import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCreateRequest
import com.ttasjwi.board.system.articlelike.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-like-application-service] ArticleLikeCreateCommandMapper 테스트")
class ArticleLikeCreateCommandMapperTest {

    private lateinit var articleLikeCreateaCommandMapper: ArticleLikeCreateCommandMapper
    private lateinit var currentTime: AppDateTime
    private lateinit var authUser: AuthUser

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleLikeCreateaCommandMapper = container.articleLikeCreateCommandMapper

        currentTime = appDateTimeFixture(minute= 8)
        container.timeManagerFixture.changeCurrentTime(currentTime)

        authUser = authUserFixture(userId = 13L, role = Role.USER)
        container.authUserLoaderFixture.changeAuthUser(authUser)
    }


    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val request = ArticleLikeCreateRequest(
            articleId = 5L
        )

        // when
        val command = articleLikeCreateaCommandMapper.mapToCommand(request)

        // then
        assertThat(command.articleId).isEqualTo(request.articleId)
        assertThat(command.likeUser).isEqualTo(authUser)
        assertThat(command.currentTime).isEqualTo(currentTime)
    }


    @Test
    @DisplayName("articleId 가 null 이면 예외 발생")
    fun testArticleIdNull() {
        // given
        val request = ArticleLikeCreateRequest(
            articleId = null
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            articleLikeCreateaCommandMapper.mapToCommand(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(NullArgumentException::class.java)
        assertThat(exceptions[0].source).isEqualTo("articleId")
    }
}
