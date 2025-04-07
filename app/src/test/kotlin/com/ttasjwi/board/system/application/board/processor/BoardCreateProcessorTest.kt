package com.ttasjwi.board.system.application.board.processor

import com.ttasjwi.board.system.application.board.dto.BoardCreateCommand
import com.ttasjwi.board.system.application.board.exception.DuplicateBoardNameException
import com.ttasjwi.board.system.application.board.exception.DuplicateBoardSlugException
import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.fixture.boardFixture
import com.ttasjwi.board.system.board.domain.service.fixture.BoardManagerFixture
import com.ttasjwi.board.system.board.domain.service.fixture.BoardStorageFixture
import com.ttasjwi.board.system.global.auth.Role
import com.ttasjwi.board.system.global.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
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
            boardFixture(
                id = 1234L,
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
            boardName = "고양이",
            boardDescription = "고양이 게시판입니다.",
            boardSlug = "cat",
            creator = authMemberFixture(memberId = 1557L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 6)
        )

        // when
        val response = processor.createBoard(command)

        // then
        val findBoard = boardStorageFixture.findByIdOrNull(response.boardId.toLong())!!

        assertThat(response.boardId).isNotNull()
        assertThat(response.name).isEqualTo(command.boardName)
        assertThat(response.description).isEqualTo(command.boardDescription)
        assertThat(response.managerId).isEqualTo(command.creator.memberId.toString())
        assertThat(response.slug).isEqualTo(command.boardSlug)
        assertThat(response.createdAt).isEqualTo(command.currentTime.toZonedDateTime())
        assertThat(findBoard).isNotNull
    }


    @Test
    @DisplayName("게시판 이름이 중복되면 예외 발생")
    fun test2() {
        // given
        val command = BoardCreateCommand(
            boardName = savedBoard.name,
            boardDescription = "고양이 게시판입니다.",
            boardSlug = "cat",
            creator = authMemberFixture(memberId = 1557L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 6)
        )

        // when
        assertThrows<DuplicateBoardNameException> { processor.createBoard(command) }
    }

    @Test
    @DisplayName("게시판 슬러그가 중복되면 예외 발생")
    fun test3() {
        // given
        val command = BoardCreateCommand(
            boardName = "고양이",
            boardDescription = "고양이 게시판입니다.",
            boardSlug = savedBoard.slug,
            creator = authMemberFixture(memberId = 1557L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 6)
        )

        // when
        assertThrows<DuplicateBoardSlugException> { processor.createBoard(command) }
    }
}
