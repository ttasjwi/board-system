package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.service.BoardManager
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BoardManagerImpl: 게시판 생성, 조작 관련 도메인 서비스")
class BoardManagerImplTest {

    private lateinit var boardManager: BoardManager

    @BeforeEach
    fun setup() {
        boardManager = BoardManagerImpl()
    }

    @Test
    @DisplayName("create : 게시판을 생성할 수 있다.")
    fun testCreate() {
        val name = "경제"
        val description = "경제 게시판입니다."
        val managerId = 13L
        val slug = "economy"
        val currentTime = appDateTimeFixture()

        val board = boardManager.create(
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
