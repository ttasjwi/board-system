package com.ttasjwi.board.system.board.application.usecase.fixture

import com.ttasjwi.board.system.board.application.usecase.BoardCreateRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BoardCreateUseCase 픽스쳐 테스트")
class BoardCreateUseCaseFixtureTest {

    private lateinit var useCaseFixture: BoardCreateUseCaseFixture

    @BeforeEach
    fun setup() {
        useCaseFixture = BoardCreateUseCaseFixture()
    }


    @Test
    @DisplayName("요청을 받고, 결과를 반환한다.")
    fun test() {
        // given
        val request = BoardCreateRequest(
            name = "고양이",
            description = "고양이 게시판입니다.",
            slug = "cat"
        )

        // when
        val createdBoard = useCaseFixture.createBoard(request).createdBoard

        // then
        assertThat(createdBoard.boardId).isNotNull()
        assertThat(createdBoard.name).isEqualTo(request.name)
        assertThat(createdBoard.description).isEqualTo(request.description)
        assertThat(createdBoard.managerId).isNotNull()
        assertThat(createdBoard.slug).isEqualTo(request.slug)
        assertThat(createdBoard.createdAt).isNotNull()
    }
}
