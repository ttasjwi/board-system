package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.model.fixture.boardDescriptionFixture
import com.ttasjwi.board.system.board.domain.model.fixture.boardNameFixture
import com.ttasjwi.board.system.board.domain.model.fixture.boardSlugFixture
import com.ttasjwi.board.system.board.domain.service.BoardManager
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
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
        val name = boardNameFixture("경제")
        val description = boardDescriptionFixture("경제 게시판입니다.")
        val managerId = memberIdFixture(13L)
        val slug = boardSlugFixture("economy")
        val currentTime = timeFixture()

        val board = boardManager.create(
            name = name,
            description = description,
            managerId = managerId,
            slug = slug,
            currentTime = currentTime
        )

        assertThat(board.id).isNull()
        assertThat(board.name).isEqualTo(name)
        assertThat(board.description).isEqualTo(description)
        assertThat(board.managerId).isEqualTo(managerId)
        assertThat(board.slug).isEqualTo(slug)
        assertThat(board.createdAt).isEqualTo(currentTime)
    }
}
