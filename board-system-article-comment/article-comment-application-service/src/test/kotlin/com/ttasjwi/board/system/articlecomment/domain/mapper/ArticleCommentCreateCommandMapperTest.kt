package com.ttasjwi.board.system.articlecomment.domain.mapper

import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCreateRequest
import com.ttasjwi.board.system.articlecomment.domain.policy.fixture.ArticleCommentContentPolicyFixture
import com.ttasjwi.board.system.articlecomment.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.AuthUser
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

@DisplayName("[article-comment-application-service] ArticleCommentCreateCommandMapper 테스트")
class ArticleCommentCreateCommandMapperTest {

    private lateinit var commandMapper: ArticleCommentCreateCommandMapper
    private lateinit var currentTime: AppDateTime
    private lateinit var articleCommentWriter: AuthUser

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        commandMapper = container.articleCommentCreateCommandMapper

        currentTime = appDateTimeFixture(minute = 13)
        container.timeManagerFixture.changeCurrentTime(currentTime)

        articleCommentWriter = authUserFixture(userId = 123456L)
        container.authUserLoaderFixture.changeAuthUser(articleCommentWriter)
    }


    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val content = "댓글 내용"
        val articleId = 1234567L
        val parentCommentId = 1232141341L

        val request = ArticleCommentCreateRequest(
            content = content,
            articleId = articleId,
            parentCommentId = parentCommentId,
        )

        // when
        val command = commandMapper.mapToCommand(request)

        // then
        assertThat(command.content).isEqualTo(content)
        assertThat(command.articleId).isEqualTo(articleId)
        assertThat(command.parentCommentId).isEqualTo(parentCommentId)
        assertThat(command.currentTime).isEqualTo(currentTime)
        assertThat(command.commentWriter).isEqualTo(articleCommentWriter)
    }


    @Test
    @DisplayName("content 가 null 일 경우 예외 발생")
    fun testContentNull() {
        // given
        val content = null
        val articleId = 1234567L
        val parentCommentId = 1232141341L

        val request = ArticleCommentCreateRequest(
            content = content,
            articleId = articleId,
            parentCommentId = parentCommentId,
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(NullArgumentException::class.java)
        assertThat(exceptions[0].source).isEqualTo("content")
    }

    @Test
    @DisplayName("content 포맷이 유효하지 않은 경우 예외 발생")
    fun testInvalidContentFormat() {
        // given
        val content = ArticleCommentContentPolicyFixture.ERROR_CONTENT
        val articleId = 1234567L
        val parentCommentId = 1232141341L

        val request = ArticleCommentCreateRequest(
            content = content,
            articleId = articleId,
            parentCommentId = parentCommentId,
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(CustomException::class.java)
        assertThat(exceptions[0].debugMessage).isEqualTo("픽스쳐 예외 : 게시글 댓글의 포맷이 유효하지 않습니다.")
    }


    @Test
    @DisplayName("articleId 가 null 일 경우 예외 발생")
    fun testArticleIdNull() {
        // given
        val content = "댓글 본문"
        val articleId = null
        val parentCommentId = 1232141341L

        val request = ArticleCommentCreateRequest(
            content = content,
            articleId = articleId,
            parentCommentId = parentCommentId,
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(NullArgumentException::class.java)
        assertThat(exceptions[0].source).isEqualTo("articleId")
    }
}
