package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.board.domain.model.fixture.*
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("게시판 테스트")
class BoardTest {

    @Nested
    @DisplayName("restore: 값으로부터 Board 를 복원한다.")
    inner class Restore {

        @Test
        fun restoreTest() {
            val id = 123434L
            val name = "경제"
            val description = "경제 게시판입니다."
            val managerId = 13L
            val slug = "economy"
            val createdAt = timeFixture()
            val board = Board.restore(
                id = id,
                name = name,
                description = description,
                managerId = managerId,
                slug = slug,
                createdAt = createdAt
            )

            assertThat(board.id).isEqualTo(boardIdFixture(id))
            assertThat(board.name).isEqualTo(boardNameFixture(name))
            assertThat(board.description).isEqualTo(boardDescriptionFixture(description))
            assertThat(board.managerId).isEqualTo(memberIdFixture(managerId))
            assertThat(board.slug).isEqualTo(boardSlugFixture(slug))
            assertThat(board.createdAt).isEqualTo(createdAt)
        }
    }


    @Nested
    @DisplayName("toString")
    inner class ToString {

        @Test
        @DisplayName("toString 이 의도한 대로 문자열을 반환하는 지 테스트")
        fun test() {
            val board = boardFixtureRegistered()

            assertThat(board.toString()).isEqualTo(
                "Board(id=${board.id}, name=${board.name}, description=${board.description}, managerId=${board.managerId}, slug=${board.slug}, createdAt=${board.createdAt})"
            )
        }
    }

    @Nested
    @DisplayName("initId : 게시판의 아이디를 초기화한다.")
    inner class InitId {

        @Test
        @DisplayName("initId : 아이디 초기화")
        fun initIdTest() {
            val board = boardFixtureNotRegistered()
            val boardId = boardIdFixture(value = 133L)
            board.initId(boardId)
            assertThat(board.id).isEqualTo(boardId)
        }
    }
}
