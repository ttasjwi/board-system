package com.ttasjwi.board.system.board.application.service

import com.ttasjwi.board.system.auth.domain.model.AuthMember
import com.ttasjwi.board.system.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.auth.domain.service.fixture.AuthMemberLoaderFixture
import com.ttasjwi.board.system.board.application.mapper.BoardCreateCommandMapper
import com.ttasjwi.board.system.board.application.processor.BoardCreateProcessor
import com.ttasjwi.board.system.board.application.usecase.BoardCreateRequest
import com.ttasjwi.board.system.board.domain.model.fixture.boardIdFixture
import com.ttasjwi.board.system.board.domain.service.fixture.*
import com.ttasjwi.board.system.core.application.fixture.TransactionRunnerFixture
import com.ttasjwi.board.system.core.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

@DisplayName("BoardCreateApplicationService: 게시판 생성 요청을 받아 처리하는 애플리케이션 서비스")
class BoardCreateApplicationServiceTest {

    private lateinit var applicationService: BoardCreateApplicationService
    private lateinit var authMember: AuthMember
    private lateinit var currentTime: ZonedDateTime
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
        currentTime = timeFixture(minute = 6)
        timeManager.changeCurrentTime(currentTime)

        boardStorageFixture = BoardStorageFixture()

        applicationService = BoardCreateApplicationService(
            processor = BoardCreateProcessor(
                boardStorage = boardStorageFixture,
                boardManager = BoardManagerFixture()
            ),
            commandMapper = BoardCreateCommandMapper(
                boardNameCreator = BoardNameCreatorFixture(),
                boardDescriptionCreator = BoardDescriptionCreatorFixture(),
                boardSlugCreator = BoardSlugCreatorFixture(),
                authMemberLoader = authMemberLoader,
                timeManager = timeManager
            ),
            transactionRunner = TransactionRunnerFixture()
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
        val result = applicationService.createBoard(request)

        // then
        val createdBoard = result.createdBoard
        val findBoard = boardStorageFixture.findByIdOrNull(boardIdFixture(createdBoard.boardId))!!

        assertThat(createdBoard.boardId).isNotNull()
        assertThat(createdBoard.name).isEqualTo(request.name)
        assertThat(createdBoard.description).isEqualTo(request.description)
        assertThat(createdBoard.managerId).isEqualTo(authMember.memberId.value)
        assertThat(createdBoard.slug).isEqualTo(request.slug)
        assertThat(createdBoard.createdAt).isEqualTo(currentTime)
        assertThat(findBoard).isNotNull
    }
}
