package com.ttasjwi.board.system.application.board.mapper

import com.ttasjwi.board.system.application.board.usecase.BoardCreateRequest
import com.ttasjwi.board.system.board.domain.service.fixture.BoardDescriptionManagerFixture
import com.ttasjwi.board.system.board.domain.service.fixture.BoardNameManagerFixture
import com.ttasjwi.board.system.board.domain.service.fixture.BoardSlugManagerFixture
import com.ttasjwi.board.system.global.auth.AuthMember
import com.ttasjwi.board.system.global.auth.Role
import com.ttasjwi.board.system.global.auth.fixture.AuthMemberLoaderFixture
import com.ttasjwi.board.system.global.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.global.exception.CustomException
import com.ttasjwi.board.system.global.exception.NullArgumentException
import com.ttasjwi.board.system.global.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.global.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("BoardCreateCommandMapper: 게시판 생성명령 매퍼")
class BoardCreateCommandMapperTest {

    private lateinit var commandMapper: BoardCreateCommandMapper
    private lateinit var currentTime: AppDateTime
    private lateinit var authMember: AuthMember

    @BeforeEach
    fun setup() {
        val timeManager = TimeManagerFixture()
        currentTime = appDateTimeFixture(minute = 6)
        timeManager.changeCurrentTime(currentTime)

        val authMemberLoader = AuthMemberLoaderFixture()
        authMember = authMemberFixture(
            memberId = 1557L,
            role = Role.USER
        )
        authMemberLoader.changeAuthMember(authMember)

        commandMapper = BoardCreateCommandMapper(
            boardNameManager = BoardNameManagerFixture(),
            boardDescriptionManager = BoardDescriptionManagerFixture(),
            boardSlugManager = BoardSlugManagerFixture(),
            authMemberLoader = authMemberLoader,
            timeManager = timeManager
        )
    }

    @Test
    @DisplayName("성공 테스트")
    fun test1() {
        // given
        val request = BoardCreateRequest(
            name = "고양이",
            description = "고양이 게시판입니다.",
            slug = "cat"
        )

        // when
        val command = commandMapper.mapToCommand(request)

        // then
        assertThat(command.boardName).isEqualTo(request.name!!)
        assertThat(command.boardDescription).isEqualTo(request.description!!)
        assertThat(command.boardSlug).isEqualTo(request.slug!!)
        assertThat(command.currentTime).isEqualTo(currentTime)
        assertThat(command.creator).isEqualTo(authMember)
    }


    @Test
    @DisplayName("게시판 이름이 누락되면 예외 발생")
    fun test2() {
        // given
        val request = BoardCreateRequest(
            name = null,
            description = "고양이 게시판입니다.",
            slug = "cat"
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("boardName")
    }

    @Test
    @DisplayName("게시판 설명이 누락되면 예외 발생")
    fun test3() {
        // given
        val request = BoardCreateRequest(
            name = "고양이",
            description = null,
            slug = "cat"
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("boardDescription")
    }


    @Test
    @DisplayName("게시판 슬러그가 누락되면 예외 발생")
    fun test4() {
        // given
        val request = BoardCreateRequest(
            name = "고양이",
            description = "고양이 게시판입니다.",
            slug = null
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("boardSlug")
    }

    @Test
    @DisplayName("게시판 이름 포맷이 잘 못 됐다면 예외 발생")
    fun test5() {
        // given
        val request = BoardCreateRequest(
            name = BoardNameManagerFixture.ERROR_NAME,
            description = "고양이 게시판입니다.",
            slug = "cat"
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(CustomException::class.java)
        assertThat(exception.source).isEqualTo("boardName")
    }

    @Test
    @DisplayName("게시판 설명 포맷이 잘 못 됐다면 예외 발생")
    fun test6() {
        // given
        val request = BoardCreateRequest(
            name = "고양이",
            description = BoardDescriptionManagerFixture.ERROR_DESCRIPTION,
            slug = "cat"
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(CustomException::class.java)
        assertThat(exception.source).isEqualTo("boardDescription")
    }

    @Test
    @DisplayName("게시판 슬러그 포맷이 잘 못 됐다면 예외 발생")
    fun test7() {
        // given
        val request = BoardCreateRequest(
            name = "고양이",
            description = "고양이 게시판입니다.",
            slug = BoardSlugManagerFixture.ERROR_SLUG
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(CustomException::class.java)
        assertThat(exception.source).isEqualTo("boardSlug")
    }

}
