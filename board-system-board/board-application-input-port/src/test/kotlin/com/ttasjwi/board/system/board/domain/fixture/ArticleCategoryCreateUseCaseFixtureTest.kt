package com.ttasjwi.board.system.board.domain.fixture

import com.ttasjwi.board.system.board.domain.ArticleCategoryCreateRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ArticleCreateUseCase 픽스쳐 테스트")
class ArticleCategoryCreateUseCaseFixtureTest {

    private lateinit var useCaseFixture: ArticleCategoryCreateUseCaseFixture

    @BeforeEach
    fun setup() {
        useCaseFixture = ArticleCategoryCreateUseCaseFixture()
    }

    @Test
    @DisplayName("요청을 받고, 결과를 반환한다.")
    fun test() {
        // given
        val boardId = 12345L
        val request = ArticleCategoryCreateRequest(
            name = "일반",
            slug = "general",
            allowSelfDelete = true,
            allowLike = true,
            allowDislike = true,
        )

        // when
        val response = useCaseFixture.create(boardId, request)

        // then
        assertThat(response.articleCategoryId).isNotNull()
        assertThat(response.boardId).isEqualTo(response.boardId)
        assertThat(response.name).isEqualTo(request.name)
        assertThat(response.slug).isEqualTo(request.slug)
        assertThat(response.allowSelfDelete).isEqualTo(request.allowSelfDelete)
        assertThat(response.allowLike).isEqualTo(request.allowLike)
        assertThat(response.allowDislike).isEqualTo(request.allowDislike)
        assertThat(response.createdAt).isNotNull()
    }
}
