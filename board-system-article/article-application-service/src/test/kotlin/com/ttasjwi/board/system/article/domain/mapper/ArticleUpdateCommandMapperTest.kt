package com.ttasjwi.board.system.article.domain.mapper

import com.ttasjwi.board.system.article.domain.ArticleUpdateRequest
import com.ttasjwi.board.system.article.domain.policy.fixture.ArticleContentPolicyFixture
import com.ttasjwi.board.system.article.domain.policy.fixture.ArticleTitlePolicyFixture
import com.ttasjwi.board.system.article.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-application-service] ArticleUpdateCommandMapper 테스트")
class ArticleUpdateCommandMapperTest {
    private lateinit var articleUpdateCommandMapper: ArticleUpdateCommandMapper
    private lateinit var authUser: AuthUser
    private lateinit var currentTime: AppDateTime

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleUpdateCommandMapper = container.articleUpdateCommandMapper

        currentTime = appDateTimeFixture(minute = 14)
        authUser = authUserFixture(userId = 1234558L, role = Role.USER)

        container.timeManagerFixture.changeCurrentTime(currentTime)
        container.authUserLoaderFixture.changeAuthUser(authUser)
    }


    @Test
    @DisplayName("입력값들이 유효하면 명령이 생성된다.")
    fun successTest() {
        // given
        val articleId = 123455L
        val request = ArticleUpdateRequest(
            title = "새 제목",
            content = "새 본문",
        )

        // when
        val command = articleUpdateCommandMapper.mapToCommand(articleId, request)

        // then
        assertThat(command.articleId).isEqualTo(articleId)
        assertThat(command.title).isEqualTo(request.title)
        assertThat(command.content).isEqualTo(request.content)
        assertThat(command.authUser).isEqualTo(authUser)
        assertThat(command.currentTime).isEqualTo(currentTime)
    }

    @Test
    @DisplayName("게시글 제목이 누락되면 예외 발생")
    fun titleNullTest() {
        // given
        val articleId = 123455L
        val request = ArticleUpdateRequest(
            title = null,
            content = "새 본문",
        )

        // when
        val ex = assertThrows<ValidationExceptionCollector> {
            articleUpdateCommandMapper.mapToCommand(articleId, request)
        }

        // then
        val exceptions = ex.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(NullArgumentException::class.java)
        assertThat(exceptions[0].source).isEqualTo("title")
    }

    @Test
    @DisplayName("제목 포맷이 유효하지 않으면 예외 발생")
    fun invalidTitleFormatTest() {
        // given
        val articleId = 123455L
        val request = ArticleUpdateRequest(
            title = ArticleTitlePolicyFixture.ERROR_TITLE,
            content = "새 본문",
        )

        // when
        val ex = assertThrows<ValidationExceptionCollector> {
            articleUpdateCommandMapper.mapToCommand(articleId, request)
        }

        // then
        val exceptions = ex.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(CustomException::class.java)
        assertThat(exceptions[0].message).isEqualTo("픽스쳐 예외 : 게시글 제목의 포맷이 유효하지 않습니다.")
    }

    @Test
    @DisplayName("게시글 본문이 누락되면 예외 발생")
    fun contentNullTest() {
        // given
        val articleId = 123455L
        val request = ArticleUpdateRequest(
            title = "새 제목",
            content = null,
        )

        // when
        val ex = assertThrows<ValidationExceptionCollector> {
            articleUpdateCommandMapper.mapToCommand(articleId, request)
        }

        // then
        val exceptions = ex.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(NullArgumentException::class.java)
        assertThat(exceptions[0].source).isEqualTo("content")
    }

    @Test
    @DisplayName("본문 포맷이 유효하지 않으면 예외 발생")
    fun invalidContentFormatTest() {
        // given
        val articleId = 123455L
        val request = ArticleUpdateRequest(
            title = "새 제목",
            content = ArticleContentPolicyFixture.ERROR_CONTENT,
        )

        // when
        val ex = assertThrows<ValidationExceptionCollector> {
            articleUpdateCommandMapper.mapToCommand(articleId, request)
        }

        // then
        val exceptions = ex.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(CustomException::class.java)
        assertThat(exceptions[0].message).isEqualTo("픽스쳐 예외 : 게시글 본문의 포맷이 유효하지 않습니다.")
    }
}
