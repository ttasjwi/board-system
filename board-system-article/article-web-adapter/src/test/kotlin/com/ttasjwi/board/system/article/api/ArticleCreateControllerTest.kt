package com.ttasjwi.board.system.article.api

import com.ttasjwi.board.system.article.domain.ArticleCreateRequest
import com.ttasjwi.board.system.article.domain.ArticleCreateResponse
import com.ttasjwi.board.system.article.domain.fixture.ArticleCreateUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("ArticleCreateController: 게시글 생성 컨트롤러")
class ArticleCreateControllerTest {

    private lateinit var controller: ArticleCreateController

    @BeforeEach
    fun setup() {
        controller = ArticleCreateController(ArticleCreateUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 통해 요청을 처리하고, 201 응답을 반환한다.")
    fun test() {
        // given
        val request = ArticleCreateRequest(
            title = "고양이는 귀여워요",
            content = "사실 제가 더 귀여워요",
            articleCategoryId = 12345L
        )

        // when
        val responseEntity = controller.create(request)
        val response = responseEntity.body as ArticleCreateResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.articleId).isNotNull()
        assertThat(response.title).isEqualTo(request.title)
        assertThat(response.content).isEqualTo(request.content)
        assertThat(response.boardId).isNotNull()
        assertThat(response.articleCategoryId).isEqualTo(request.articleCategoryId.toString())
        assertThat(response.writerId).isNotNull()
        assertThat(response.writerNickname).isNotNull()
        assertThat(response.createdAt).isNotNull()
        assertThat(response.writerId).isNotNull()
    }
}
