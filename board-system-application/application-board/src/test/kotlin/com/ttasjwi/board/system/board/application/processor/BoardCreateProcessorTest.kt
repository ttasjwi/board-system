package com.ttasjwi.board.system.board.application.processor

import com.ttasjwi.board.system.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.board.application.dto.BoardCreateCommand
import com.ttasjwi.board.system.board.application.exception.DuplicateBoardNameException
import com.ttasjwi.board.system.board.application.exception.DuplicateBoardSlugException
import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.fixture.*
import com.ttasjwi.board.system.board.domain.service.fixture.BoardManagerFixture
import com.ttasjwi.board.system.board.domain.service.fixture.BoardStorageFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("BoardCreateProcessor: 게시판 생성을 실질적으로 수행하는 처리자")
class BoardCreateProcessorTest {

    private lateinit var processor: BoardCreateProcessor
    private lateinit var boardStorageFixture: BoardStorageFixture
    private lateinit var savedBoard: Board

    @BeforeEach
    fun setup() {
        boardStorageFixture = BoardStorageFixture()
        processor = BoardCreateProcessor(
            boardStorage = boardStorageFixture,
            boardManager = BoardManagerFixture()
        )
        savedBoard = boardStorageFixture.save(
            boardFixtureNotRegistered(
                name = "등록된",
                slug = "registered"
            )
        )
    }


    @Test
    @DisplayName("성공 테스트")
    fun test1() {
        // given
        val command = BoardCreateCommand(
            boardName = boardNameFixture("고양이"),
            boardDescription = boardDescriptionFixture("고양이 게시판입니다."),
            boardSlug = boardSlugFixture("cat"),
            creator = authMemberFixture(memberId = 1557L, role = Role.USER),
            currentTime = timeFixture(minute = 6)
        )

        // when
        val result = processor.createBoard(command)

        // then
        val createdBoard = result.createdBoard
        val findBoard = boardStorageFixture.findByIdOrNull(boardIdFixture(createdBoard.boardId))!!

        assertThat(createdBoard.boardId).isNotNull()
        assertThat(createdBoard.name).isEqualTo(command.boardName.value)
        assertThat(createdBoard.description).isEqualTo(command.boardDescription.value)
        assertThat(createdBoard.managerId).isEqualTo(command.creator.memberId.value)
        assertThat(createdBoard.slug).isEqualTo(command.boardSlug.value)
        assertThat(createdBoard.createdAt).isEqualTo(command.currentTime)
        assertThat(findBoard).isNotNull
    }


    @Test
    @DisplayName("게시판 이름이 중복되면 예외 발생")
    fun test2() {
        // given
        val command = BoardCreateCommand(
            boardName = savedBoard.name,
            boardDescription = boardDescriptionFixture("고양이 게시판입니다."),
            boardSlug = boardSlugFixture("cat"),
            creator = authMemberFixture(memberId = 1557L, role = Role.USER),
            currentTime = timeFixture(minute = 6)
        )

        // when
        assertThrows<DuplicateBoardNameException> { processor.createBoard(command) }
    }

    @Test
    @DisplayName("게시판 슬러그가 중복되면 예외 발생")
    fun test3() {
        // given
        val command = BoardCreateCommand(
            boardName = boardNameFixture("고양이"),
            boardDescription = boardDescriptionFixture("고양이 게시판입니다."),
            boardSlug = savedBoard.slug,
            creator = authMemberFixture(memberId = 1557L, role = Role.USER),
            currentTime = timeFixture(minute = 6)
        )

        // when
        assertThrows<DuplicateBoardSlugException> { processor.createBoard(command) }
    }
}
