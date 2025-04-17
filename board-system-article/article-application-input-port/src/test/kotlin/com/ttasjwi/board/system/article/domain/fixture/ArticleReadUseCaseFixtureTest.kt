package com.ttasjwi.board.system.article.domain.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ArticleReadUseCaseFixture: 게시글 조회 유즈케이스 픽스쳐")
class ArticleReadUseCaseFixtureTest {

    private lateinit var useCaseFixture: ArticleReadUseCaseFixture

    @BeforeEach
    fun setup() {
        useCaseFixture = ArticleReadUseCaseFixture()
    }


    @Test
    @DisplayName("요청을 받고, 응답를 반환한다.")
    fun testSuccess() {
        // given
        val articleId = 12345L

        // when
        val response = useCaseFixture.read(articleId)

        // then
        assertThat(response.articleId).isEqualTo(articleId.toString())
        assertThat(response.title).isNotNull()
        assertThat(response.content).isNotNull()
        assertThat(response.boardId).isNotNull()
        assertThat(response.articleCategoryId).isNotNull()
        assertThat(response.writerId).isNotNull()
        assertThat(response.writerNickname).isNotNull()
        assertThat(response.createdAt).isNotNull()
        assertThat(response.modifiedAt).isNotNull()
    }
}
