package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.board.domain.model.fixture.boardFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("게시판 테스트")
class BoardTest {

    @Test
    @DisplayName("create() : 게시판을 생성할 수 있다.")
    fun testCreate() {
        val boardId = 1234L
        val name = "경제"
        val description = "경제 게시판입니다."
        val managerId = 13L
        val slug = "economy"
        val currentTime = appDateTimeFixture()

        val board = Board.create(
            boardId = 1234L,
            name = name,
            description = description,
            managerId = managerId,
            slug = slug,
            currentTime = currentTime
        )

        assertThat(board.boardId).isEqualTo(boardId)
        assertThat(board.name).isEqualTo(name)
        assertThat(board.description).isEqualTo(description)
        assertThat(board.managerId).isEqualTo(managerId)
        assertThat(board.slug).isEqualTo(slug)
        assertThat(board.createdAt).isEqualTo(currentTime)
    }


    @Test
    @DisplayName("restore(): 값으로부터 Board 를 복원한다.")
    fun restoreTest() {
        val boardId = 123434L
        val name = "경제"
        val description = "경제 게시판입니다."
        val managerId = 13L
        val slug = "economy"
        val createdAt = appDateTimeFixture().toLocalDateTime()
        val board = Board.restore(
            boardId = boardId,
            name = name,
            description = description,
            managerId = managerId,
            slug = slug,
            createdAt = createdAt
        )

        assertThat(board.boardId).isEqualTo(boardId)
        assertThat(board.name).isEqualTo(name)
        assertThat(board.description).isEqualTo(description)
        assertThat(board.managerId).isEqualTo(managerId)
        assertThat(board.slug).isEqualTo(slug)
        assertThat(board.createdAt.toLocalDateTime()).isEqualTo(createdAt)
    }


    @Test
    @DisplayName("toString(): 디버깅 문자열")
    fun toStringTest() {
        val board = boardFixture()

        assertThat(board.toString()).isEqualTo(
            "Board(boardId=${board.boardId}, name=${board.name}, description=${board.description}, managerId=${board.managerId}, slug=${board.slug}, createdAt=${board.createdAt})"
        )
    }

}
