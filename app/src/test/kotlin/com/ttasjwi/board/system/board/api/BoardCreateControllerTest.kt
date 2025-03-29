package com.ttasjwi.board.system.board.api

import com.ttasjwi.board.system.board.application.usecase.BoardCreateRequest
import com.ttasjwi.board.system.board.application.usecase.BoardCreateResult
import com.ttasjwi.board.system.board.application.usecase.fixture.BoardCreateUseCaseFixture
import com.ttasjwi.board.system.common.api.SuccessResponse
import com.ttasjwi.board.system.common.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*

@DisplayName("BoardCreateController 테스트")
class BoardCreateControllerTest {

    private lateinit var controller: BoardCreateController

    @BeforeEach
    fun setup() {
        controller = BoardCreateController(
            useCase = BoardCreateUseCaseFixture(),
            messageResolver = MessageResolverFixture(),
            localeManager = LocaleManagerFixture()
        )
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
        val response = responseEntity.body as SuccessResponse<BoardCreateResult>

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.isSuccess).isTrue()
        assertThat(response.code).isEqualTo("BoardCreate.Complete")
        assertThat(response.message).isEqualTo("BoardCreate.Complete.message(locale=${Locale.KOREAN},args=[])")
        assertThat(response.description).isEqualTo("BoardCreate.Complete.description(locale=${Locale.KOREAN},args=[])")

        val createdBoard = response.data.createdBoard

        assertThat(createdBoard.boardId).isNotNull()
        assertThat(createdBoard.name).isEqualTo(request.name)
        assertThat(createdBoard.description).isEqualTo(request.description)
        assertThat(createdBoard.managerId).isNotNull()
        assertThat(createdBoard.slug).isEqualTo(request.slug)
        assertThat(createdBoard.createdAt).isNotNull()
    }
}
