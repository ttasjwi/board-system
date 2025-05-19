package com.ttasjwi.board.system.articlelike.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleDislikeCountFixture 테스트")
class ArticleDislikeCountFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가진다.")
    fun test1() {
        val articleDislikeCount = articleDislikeCountFixture()

        assertThat(articleDislikeCount.articleId).isNotNull()
        assertThat(articleDislikeCount.dislikeCount).isNotNull()
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val articleId = 14578L
        val dislikeCount = 2314558888L

        val articleDislikeCount = articleDislikeCountFixture(
            articleId = articleId,
            dislikeCount = dislikeCount,
        )

        assertThat(articleDislikeCount.articleId).isEqualTo(articleId)
        assertThat(articleDislikeCount.dislikeCount).isEqualTo(dislikeCount)
    }
}
