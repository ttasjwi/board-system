package com.ttasjwi.board.system.article.domain.fixture

import com.ttasjwi.board.system.article.domain.ArticleCreateRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ArticleCreateUseCaseFixture: 게시글 생성 유즈케이스 픽스쳐")
class ArticleCreateUseCaseFixtureTest {

    private lateinit var useCaseFixture: ArticleCreateUseCaseFixture

    @BeforeEach
    fun setup() {
        useCaseFixture = ArticleCreateUseCaseFixture()
    }


    @Test
    @DisplayName("요청을 받고, 응답를 반환한다.")
    fun testSuccess() {
        // given
        val request = ArticleCreateRequest(
            title = "제목",
            content = "본문",
            articleCategoryId = 1L
        )

        // when
        val response = useCaseFixture.create(request)

        // then
        assertThat(response.articleId).isNotNull()
        assertThat(response.title).isEqualTo(request.title)
        assertThat(response.content).isEqualTo(request.content)
        assertThat(response.boardId).isNotNull()
        assertThat(response.articleCategoryId).isEqualTo(request.articleCategoryId.toString())
        assertThat(response.writerId).isNotNull()
        assertThat(response.createdAt).isNotNull()
        assertThat(response.modifiedAt).isNotNull()
    }
}
