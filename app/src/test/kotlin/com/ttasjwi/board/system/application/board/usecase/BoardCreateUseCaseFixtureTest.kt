package com.ttasjwi.board.system.application.board.usecase

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
        val response = useCaseFixture.createBoard(request)

        // then
        assertThat(response.boardId).isNotNull()
        assertThat(response.name).isEqualTo(request.name)
        assertThat(response.description).isEqualTo(request.description)
        assertThat(response.managerId).isNotNull()
        assertThat(response.slug).isEqualTo(request.slug)
        assertThat(response.createdAt).isNotNull()
    }
}
