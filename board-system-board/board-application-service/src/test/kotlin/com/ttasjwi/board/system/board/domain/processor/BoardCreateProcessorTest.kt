package com.ttasjwi.board.system.board.domain.processor

import com.ttasjwi.board.system.board.domain.dto.BoardCreateCommand
import com.ttasjwi.board.system.board.domain.exception.DuplicateBoardNameException
import com.ttasjwi.board.system.board.domain.exception.DuplicateBoardSlugException
import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.fixture.boardFixture
import com.ttasjwi.board.system.board.domain.port.fixture.BoardPersistencePortFixture
import com.ttasjwi.board.system.board.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[board-application-service] BoardCreateProcessor: 게시판 생성을 실질적으로 수행하는 처리자")
class BoardCreateProcessorTest {

    private lateinit var processor: BoardCreateProcessor
    private lateinit var boardPersistencePortFixture: BoardPersistencePortFixture
    private lateinit var savedBoard: Board

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        boardPersistencePortFixture = container.boardPersistencePortFixture
        processor = container.boardCreateProcessor
        savedBoard = boardPersistencePortFixture.save(
            boardFixture(
                boardId = 1234L,
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
            creator = authUserFixture(userId = 1557L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 6)
        )

        // when
        val response = processor.createBoard(command)

        // then
        val findBoard = boardPersistencePortFixture.findByIdOrNull(response.boardId.toLong())!!

        assertThat(response.boardId).isNotNull()
        assertThat(response.name).isEqualTo(command.boardName)
        assertThat(response.description).isEqualTo(command.boardDescription)
        assertThat(response.managerId).isEqualTo(command.creator.userId.toString())
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
            creator = authUserFixture(userId = 1557L, role = Role.USER),
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
            creator = authUserFixture(userId = 1557L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 6)
        )

        // when
        assertThrows<DuplicateBoardSlugException> { processor.createBoard(command) }
    }
}
