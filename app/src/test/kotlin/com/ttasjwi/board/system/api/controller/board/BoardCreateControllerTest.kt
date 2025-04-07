package com.ttasjwi.board.system.api.controller.board

import com.ttasjwi.board.system.board.application.usecase.BoardCreateRequest
import com.ttasjwi.board.system.board.application.usecase.BoardCreateResponse
import com.ttasjwi.board.system.board.application.usecase.fixture.BoardCreateUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("BoardCreateController 테스트")
class BoardCreateControllerTest {

    private lateinit var controller: BoardCreateController

    @BeforeEach
    fun setup() {
        controller = BoardCreateController(BoardCreateUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 그 결과를 기반으로 200 코드와 함께 응답을 반환한다.")
    fun test() {
        // given
        val request = BoardCreateRequest(
            name = "고양이",
            description = "고양이 게시판입니다.",
            slug = "cat"
        )

        // when
        val responseEntity = controller.createBoard(request)
        val response = responseEntity.body as BoardCreateResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.boardId).isNotNull()
        assertThat(response.name).isEqualTo(request.name)
        assertThat(response.description).isEqualTo(request.description)
        assertThat(response.managerId).isNotNull()
        assertThat(response.slug).isEqualTo(request.slug)
        assertThat(response.createdAt).isNotNull()
    }
}
