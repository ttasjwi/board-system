package com.ttasjwi.board.system.article.domain.mapper

import com.ttasjwi.board.system.article.domain.ArticleCreateRequest
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

@DisplayName("[article-application-service] ArticleCreateCommandMapper : 게시글 생성 명령 매퍼")
class ArticleCreateCommandMapperTest {

    private lateinit var articleCreateCommandMapper: ArticleCreateCommandMapper
    private lateinit var writer: AuthUser
    private lateinit var currentTime: AppDateTime

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleCreateCommandMapper = container.articleCreateCommandMapper

        currentTime = appDateTimeFixture(minute = 14)
        writer = authUserFixture(userId = 1234558L, role = Role.USER)

        container.timeManagerFixture.changeCurrentTime(currentTime)
        container.authMemberLoaderFixture.changeAuthUser(writer)
    }

    @Test
    @DisplayName("입력값들이 유효하면 명령이 생성된다.")
    fun successTest() {
        // given
        val request = ArticleCreateRequest(
            title = "제목",
            content = "본문",
            articleCategoryId = 123457L
        )

        // when
        val command = articleCreateCommandMapper.mapToCommand(request)

        // then
        assertThat(command.title).isEqualTo(request.title)
        assertThat(command.content).isEqualTo(request.content)
        assertThat(command.articleCategoryId).isEqualTo(request.articleCategoryId)
        assertThat(command.writer).isEqualTo(writer)
        assertThat(command.currentTime).isEqualTo(currentTime)
    }

    @Test
    @DisplayName("게시글 제목이 누락되면 예외 발생")
    fun titleNullTest() {
        // given
        val request = ArticleCreateRequest(
            title = null,
            content = "본문",
            articleCategoryId = 123457L
        )

        // when
        val ex = assertThrows<ValidationExceptionCollector> {
            articleCreateCommandMapper.mapToCommand(request)
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
        val request = ArticleCreateRequest(
            title = ArticleTitlePolicyFixture.ERROR_TITLE,
            content = "content",
            articleCategoryId = 123457L
        )

        // when
        val ex = assertThrows<ValidationExceptionCollector> {
            articleCreateCommandMapper.mapToCommand(request)
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
        val request = ArticleCreateRequest(
            title = "제목",
            content = null,
            articleCategoryId = 123457L
        )

        // when
        val ex = assertThrows<ValidationExceptionCollector> {
            articleCreateCommandMapper.mapToCommand(request)
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
        val request = ArticleCreateRequest(
            title = "title",
            content = ArticleContentPolicyFixture.ERROR_CONTENT,
            articleCategoryId = 123457L
        )

        // when
        val ex = assertThrows<ValidationExceptionCollector> {
            articleCreateCommandMapper.mapToCommand(request)
        }

        // then
        val exceptions = ex.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(CustomException::class.java)
        assertThat(exceptions[0].message).isEqualTo("픽스쳐 예외 : 게시글 본문의 포맷이 유효하지 않습니다.")
    }

    @Test
    @DisplayName("게시글 카테고리 아이디가 누락되면 예외 발생")
    fun articleCategoryIdNullTest() {
        // given
        val request = ArticleCreateRequest(
            title = "제목",
            content = "content",
            articleCategoryId = null
        )

        // when
        val ex = assertThrows<ValidationExceptionCollector> {
            articleCreateCommandMapper.mapToCommand(request)
        }

        // then
        val exceptions = ex.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(NullArgumentException::class.java)
        assertThat(exceptions[0].source).isEqualTo("articleCategoryId")
    }
}
