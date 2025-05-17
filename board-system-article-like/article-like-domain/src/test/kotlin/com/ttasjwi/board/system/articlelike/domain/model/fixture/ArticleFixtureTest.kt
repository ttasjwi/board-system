package com.ttasjwi.board.system.articlelike.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleFixture 테스트")
class ArticleFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가진다.")
    fun test1() {
        val article = articleFixture()

        assertThat(article.articleId).isNotNull()
        assertThat(article.articleCategoryId).isNotNull()
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val articleId = 14578L
        val articleCategoryId = 2314558888L

        val article = articleFixture(
            articleId = articleId,
            articleCategoryId = articleCategoryId,
        )

        assertThat(article.articleId).isEqualTo(articleId)
        assertThat(article.articleCategoryId).isEqualTo(articleCategoryId)
    }
}
