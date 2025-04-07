package com.ttasjwi.board.system.board.application.service

import com.ttasjwi.board.system.board.application.mapper.BoardCreateCommandMapper
import com.ttasjwi.board.system.board.application.processor.BoardCreateProcessor
import com.ttasjwi.board.system.board.application.usecase.BoardCreateRequest
import com.ttasjwi.board.system.board.domain.service.fixture.*
import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.common.auth.fixture.AuthMemberLoaderFixture
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BoardCreateApplicationService: 게시판 생성 요청을 받아 처리하는 애플리케이션 서비스")
class BoardCreateApplicationServiceTest {

    private lateinit var applicationService: BoardCreateApplicationService
    private lateinit var authMember: AuthMember
    private lateinit var currentTime: AppDateTime
    private lateinit var boardStorageFixture: BoardStorageFixture

    @BeforeEach
    fun setup() {
        val authMemberLoader = AuthMemberLoaderFixture()
        authMember = authMemberFixture(
            memberId = 1557L,
            role = Role.USER
        )
        authMemberLoader.changeAuthMember(authMember)

        val timeManager = TimeManagerFixture()
        currentTime = appDateTimeFixture(minute = 6)
        timeManager.changeCurrentTime(currentTime)

        boardStorageFixture = BoardStorageFixture()

        applicationService = BoardCreateApplicationService(
            processor = BoardCreateProcessor(
                boardStorage = boardStorageFixture,
                boardManager = BoardManagerFixture()
            ),
            commandMapper = BoardCreateCommandMapper(
                boardNameManager = BoardNameManagerFixture(),
                boardDescriptionManager = BoardDescriptionManagerFixture(),
                boardSlugManager = BoardSlugManagerFixture(),
                authMemberLoader = authMemberLoader,
                timeManager = timeManager
            ),
        )
    }


    @Test
    @DisplayName("게시판 생성 애플리케이션 기능이 잘 동작해야한다.")
    fun test() {
        // given
        val request = BoardCreateRequest(
            name = "고양이",
            description = "고양이 게시판입니다.",
            slug = "cat"
        )

        // when
        val response = applicationService.createBoard(request)

        // then
        val findBoard = boardStorageFixture.findByIdOrNull(response.boardId.toLong())!!
        assertThat(response.boardId).isNotNull()
        assertThat(response.name).isEqualTo(request.name)
        assertThat(response.description).isEqualTo(request.description)
        assertThat(response.managerId).isEqualTo(authMember.memberId.toString())
        assertThat(response.slug).isEqualTo(request.slug)
        assertThat(response.createdAt).isEqualTo(currentTime.toZonedDateTime())
        assertThat(findBoard).isNotNull
    }
}
