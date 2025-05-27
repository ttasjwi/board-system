package com.ttasjwi.board.system.articleread.domain.port.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-read-application-output-port] ArticleViewCountStorageFixture 테스트")
class ArticleViewCountStorageFixtureTest {

    private lateinit var articleViewCountStorageFixture: ArticleViewCountStorageFixture


    @BeforeEach
    fun setup() {
        articleViewCountStorageFixture = ArticleViewCountStorageFixture()
    }

    @Test
    @DisplayName("조회수 저장 후 조회 테스트")
    fun testSuccess() {
        // given
        val articleId = 34L
        val viewCount = 348L


        articleViewCountStorageFixture.save(articleId, viewCount)

        // when
        val findViewCount = articleViewCountStorageFixture.readViewCount(articleId)

        // then
        assertThat(findViewCount).isEqualTo(viewCount)
    }


    @Test
    @DisplayName("조회수가 저장되어 있지 않다면, 조회수 조회 시도시, 0이 반환된다.")
    fun testNotFound() {
        // given
        // when
        val findViewCount = articleViewCountStorageFixture.readViewCount(131415L)

        // then
        assertThat(findViewCount).isEqualTo(0L)
    }
}
