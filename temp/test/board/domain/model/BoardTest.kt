package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.board.domain.model.fixture.boardFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
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
            val createdAt = appDateTimeFixture().toLocalDateTime()
            val board = Board.restore(
                id = id,
                name = name,
                description = description,
                managerId = managerId,
                slug = slug,
                createdAt = createdAt
            )

            assertThat(board.id).isEqualTo(id)
            assertThat(board.name).isEqualTo(name)
            assertThat(board.description).isEqualTo(description)
            assertThat(board.managerId).isEqualTo(managerId)
            assertThat(board.slug).isEqualTo(slug)
            assertThat(board.createdAt.toLocalDateTime()).isEqualTo(createdAt)
        }
    }

    @Nested
    @DisplayName("toString")
    inner class ToString {

        @Test
        @DisplayName("toString 이 의도한 대로 문자열을 반환하는 지 테스트")
        fun test() {
            val board = boardFixture()

            assertThat(board.toString()).isEqualTo(
                "Board(id=${board.id}, name=${board.name}, description=${board.description}, managerId=${board.managerId}, slug=${board.slug}, createdAt=${board.createdAt})"
            )
        }
    }

}
