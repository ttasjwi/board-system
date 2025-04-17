package com.ttasjwi.board.system.article.api

import com.ttasjwi.board.system.article.domain.ArticleReadResponse
import com.ttasjwi.board.system.article.domain.fixture.ArticleReadUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("[article-web-adapter] ArticleReadController: 게시글 조회 컨트롤러")
class ArticleReadControllerTest {

    private lateinit var controller: ArticleReadController

    @BeforeEach
    fun setup() {
        controller = ArticleReadController(ArticleReadUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 통해 요청을 처리하고, 200 응답을 반환한다.")
    fun test() {
        // given
        val articleId = 1245567L

        // when
        val responseEntity = controller.read(articleId)
        val response = responseEntity.body as ArticleReadResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.articleId).isEqualTo(articleId.toString())
        assertThat(response.title).isNotNull()
        assertThat(response.content).isNotNull()
        assertThat(response.boardId).isNotNull()
        assertThat(response.articleCategoryId).isNotNull()
        assertThat(response.writerId).isNotNull()
        assertThat(response.writerNickname).isNotNull()
        assertThat(response.createdAt).isNotNull()
        assertThat(response.writerId).isNotNull()
    }
}
