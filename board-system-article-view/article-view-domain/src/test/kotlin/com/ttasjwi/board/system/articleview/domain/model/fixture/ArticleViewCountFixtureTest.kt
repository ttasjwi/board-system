package com.ttasjwi.board.system.articleview.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-view-domain] ArticleViewCountFixture 테스트")
class ArticleViewCountFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가진다.")
    fun test1() {
        val articleViewCount = articleViewCountFixture()

        assertThat(articleViewCount.articleId).isNotNull()
        assertThat(articleViewCount.viewCount).isNotNull()
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val articleId = 14578L
        val viewCount = 2314558888L

        val articleViewCount = articleViewCountFixture(
            articleId = articleId,
            viewCount = viewCount,
        )

        assertThat(articleViewCount.articleId).isEqualTo(articleId)
        assertThat(articleViewCount.viewCount).isEqualTo(viewCount)
    }
}
