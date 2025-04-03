package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.model.fixture.boardSlugFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BoardManager 픽스쳐 테스트")
class BoardManagerFixtureTest {

    private lateinit var boardManagerFixture: BoardManagerFixture

    @BeforeEach
    fun setup() {
        boardManagerFixture = BoardManagerFixture()
    }

    @Test
    @DisplayName("create : 게시판을 생성할 수 있다.")
    fun testCreate() {
        val name = "경제"
        val description = "경제 게시판입니다."
        val managerId = 13L
        val slug = boardSlugFixture("economy")
        val currentTime = appDateTimeFixture()

        val board = boardManagerFixture.create(
            name = name,
            description = description,
            managerId = managerId,
            slug = slug,
            currentTime = currentTime
        )

        assertThat(board.id).isNotNull()
        assertThat(board.name).isEqualTo(name)
        assertThat(board.description).isEqualTo(description)
        assertThat(board.managerId).isEqualTo(managerId)
        assertThat(board.slug).isEqualTo(slug)
        assertThat(board.createdAt).isEqualTo(currentTime)
    }

}
