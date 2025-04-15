package com.ttasjwi.board.system.board.domain.mapper

import com.ttasjwi.board.system.board.domain.BoardArticleCategoryCreateRequest
import com.ttasjwi.board.system.board.domain.policy.fixture.BoardArticleCategoryNamePolicyFixture
import com.ttasjwi.board.system.board.domain.policy.fixture.BoardArticleCategorySlugPolicyFixture
import com.ttasjwi.board.system.board.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authMemberFixture
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

@DisplayName("BoardArticleCategoryCreateCommandMapper: 게시글 카테고리 생성명령 매퍼")
class BoardArticleCategoryCreateCommandMapperTest {

    private lateinit var commandMapper: BoardArticleCategoryCreateCommandMapper
    private lateinit var currentTime: AppDateTime
    private lateinit var authMember: AuthMember

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()

        commandMapper = container.boardArticleCategoryCreateCommandMapper
        currentTime = appDateTimeFixture(minute = 5)
        container.timeManagerFixture.changeCurrentTime(currentTime)

        authMember = authMemberFixture(
            memberId = 1557L,
            role = Role.USER
        )
        container.authMemberLoaderFixture.changeAuthMember(authMember)
    }


    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val boardId = 12L
        val request = BoardArticleCategoryCreateRequest(
            name = "일반",
            slug = "general",
            allowSelfDelete = true,
            allowLike = false,
            allowDislike = true,
        )

        // when
        val command = commandMapper.mapToCommand(boardId, request)

        // then
        assertThat(command.boardId).isEqualTo(boardId)
        assertThat(command.creator).isEqualTo(authMember)
        assertThat(command.boardArticleCategoryName).isEqualTo(request.name)
        assertThat(command.boardArticleCategorySlug).isEqualTo(request.slug)
        assertThat(command.allowSelfDelete).isEqualTo(request.allowSelfDelete)
        assertThat(command.allowLike).isEqualTo(request.allowLike)
        assertThat(command.allowDislike).isEqualTo(request.allowDislike)
        assertThat(command.currentTime).isEqualTo(currentTime)
    }

    @Test
    @DisplayName("카테고리명 필드가 null 이면 예외가 발생한다.")
    fun testBoardArticleCategoryNameNull() {
        // given
        val boardId = 12L
        val request = BoardArticleCategoryCreateRequest(
            name = null,
            slug = "general",
            allowSelfDelete = true,
            allowLike = false,
            allowDislike = true,
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> { commandMapper.mapToCommand(boardId, request) }

        // then
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("name")
    }

    @Test
    @DisplayName("카테고리 이름 필드 포맷이 유효하지 않으면 예외가 발생한다.")
    fun testInvalidBoardArticleCategoryNameFormat() {
        // given
        val boardId = 12L
        val request = BoardArticleCategoryCreateRequest(
            name = BoardArticleCategoryNamePolicyFixture.ERROR_NAME,
            slug = "general",
            allowSelfDelete = true,
            allowLike = false,
            allowDislike = true,
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> { commandMapper.mapToCommand(boardId, request) }

        // then
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(CustomException::class.java)
        assertThat(exception.code).isEqualTo("Error.InvalidBoardArticleCategoryNameFormat")
        assertThat(exception.debugMessage).isEqualTo("픽스쳐 예외 : 잘못된 게시글 카테고리 이름 포맷")
    }

    @Test
    @DisplayName("카테고리 슬러그 필드가 null 이면 예외가 발생한다.")
    fun testBoardArticleCategorySlugNull() {
        // given
        val boardId = 12L
        val request = BoardArticleCategoryCreateRequest(
            name = "일반",
            slug = null,
            allowSelfDelete = true,
            allowLike = false,
            allowDislike = true,
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> { commandMapper.mapToCommand(boardId, request) }

        // then
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("slug")
    }

    @Test
    @DisplayName("카테고리 슬러그 필드 포맷이 유효하지 않으면 예외가 발생한다.")
    fun testInvalidBoardArticleCategorySlugFormat() {
        // given
        val boardId = 12L
        val request = BoardArticleCategoryCreateRequest(
            name = "일반",
            slug = BoardArticleCategorySlugPolicyFixture.ERROR_SLUG,
            allowSelfDelete = true,
            allowLike = false,
            allowDislike = true,
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> { commandMapper.mapToCommand(boardId, request) }

        // then
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(CustomException::class.java)
        assertThat(exception.code).isEqualTo("Error.InvalidBoardArticleCategorySlugFormat")
        assertThat(exception.debugMessage).isEqualTo("픽스쳐 예외 : 잘못된 게시글 카테고리 슬러그 포맷")
    }

    @Test
    @DisplayName("allowSelfDelete 필드가 null 이면 예외가 발생한다.")
    fun testAllowSelfDeleteNull() {
        // given
        val boardId = 12L
        val request = BoardArticleCategoryCreateRequest(
            name = "일반",
            slug = "general",
            allowSelfDelete = null,
            allowLike = false,
            allowDislike = true,
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> { commandMapper.mapToCommand(boardId, request) }

        // then
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("allowSelfDelete")
    }

    @Test
    @DisplayName("allowLike 필드가 null 이면 예외가 발생한다.")
    fun testAllowLikeNull() {
        // given
        val boardId = 12L
        val request = BoardArticleCategoryCreateRequest(
            name = "일반",
            slug = "general",
            allowSelfDelete = true,
            allowLike = null,
            allowDislike = true,
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> { commandMapper.mapToCommand(boardId, request) }

        // then
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("allowLike")
    }

    @Test
    @DisplayName("allowDislike 필드가 null 이면 예외가 발생한다.")
    fun testAllowDislikeNull() {
        // given
        val boardId = 12L
        val request = BoardArticleCategoryCreateRequest(
            name = "일반",
            slug = "general",
            allowSelfDelete = true,
            allowLike = false,
            allowDislike = null,
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> { commandMapper.mapToCommand(boardId, request) }

        // then
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("allowDislike")
    }
}
