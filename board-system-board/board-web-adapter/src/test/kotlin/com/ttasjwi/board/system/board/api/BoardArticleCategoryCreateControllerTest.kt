package com.ttasjwi.board.system.board.api

import com.ttasjwi.board.system.board.domain.BoardArticleCategoryCreateRequest
import com.ttasjwi.board.system.board.domain.BoardArticleCategoryCreateResponse
import com.ttasjwi.board.system.board.domain.BoardCreateRequest
import com.ttasjwi.board.system.board.domain.BoardCreateResponse
import com.ttasjwi.board.system.board.domain.fixture.BoardArticleCategoryCreateUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("BoardArticleCategoryCreateController 테스트")
class BoardArticleCategoryCreateControllerTest {

    private lateinit var controller: BoardArticleCategoryCreateController

    @BeforeEach
    fun setup() {
        controller = BoardArticleCategoryCreateController(BoardArticleCategoryCreateUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 통해 요청을 처리하고, 201 응답을 반환한다.")
    fun test() {
        // given
        val boardId = 1L
        val request = BoardArticleCategoryCreateRequest(
            name = "고양이",
            slug = "cat",
            allowSelfDelete = true,
            allowLike = true,
            allowDislike = true,
        )

        // when
        val responseEntity = controller.createBoardCategory(boardId, request)
        val response = responseEntity.body as BoardArticleCategoryCreateResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.boardArticleCategoryId).isNotNull()
        assertThat(response.boardId).isEqualTo(boardId.toString())
        assertThat(response.name).isEqualTo(request.name)
        assertThat(response.slug).isEqualTo(request.slug)
        assertThat(response.allowSelfDelete).isEqualTo(request.allowSelfDelete)
        assertThat(response.allowLike).isEqualTo(request.allowLike)
        assertThat(response.allowDislike).isEqualTo(request.allowDislike)
        assertThat(response.createdAt).isNotNull()
    }
}
