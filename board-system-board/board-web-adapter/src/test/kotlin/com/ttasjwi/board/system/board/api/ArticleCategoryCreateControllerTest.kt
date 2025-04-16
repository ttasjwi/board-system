package com.ttasjwi.board.system.board.api

import com.ttasjwi.board.system.board.domain.ArticleCategoryCreateRequest
import com.ttasjwi.board.system.board.domain.ArticleCategoryCreateResponse
import com.ttasjwi.board.system.board.domain.fixture.ArticleCategoryCreateUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("ArticleCategoryCreateController 테스트")
class ArticleCategoryCreateControllerTest {

    private lateinit var controller: ArticleCategoryCreateController

    @BeforeEach
    fun setup() {
        controller = ArticleCategoryCreateController(ArticleCategoryCreateUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 통해 요청을 처리하고, 201 응답을 반환한다.")
    fun test() {
        // given
        val boardId = 1L
        val request = ArticleCategoryCreateRequest(
            name = "고양이",
            slug = "cat",
            allowSelfDelete = true,
            allowLike = true,
            allowDislike = true,
        )

        // when
        val responseEntity = controller.createArticleCategory(boardId, request)
        val response = responseEntity.body as ArticleCategoryCreateResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.articleCategoryId).isNotNull()
        assertThat(response.boardId).isEqualTo(boardId.toString())
        assertThat(response.name).isEqualTo(request.name)
        assertThat(response.slug).isEqualTo(request.slug)
        assertThat(response.allowSelfDelete).isEqualTo(request.allowSelfDelete)
        assertThat(response.allowLike).isEqualTo(request.allowLike)
        assertThat(response.allowDislike).isEqualTo(request.allowDislike)
        assertThat(response.createdAt).isNotNull()
    }
}
