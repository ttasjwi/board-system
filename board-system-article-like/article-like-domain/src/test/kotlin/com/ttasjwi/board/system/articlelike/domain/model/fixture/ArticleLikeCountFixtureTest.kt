package com.ttasjwi.board.system.articlelike.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleLikeCountFixture 테스트")
class ArticleLikeCountFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가진다.")
    fun test1() {
        val articleLikeCount = articleLikeCountFixture()

        assertThat(articleLikeCount.articleId).isNotNull()
        assertThat(articleLikeCount.likeCount).isNotNull()
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val articleId = 14578L
        val likeCount = 2314558888L

        val articleLikeCount = articleLikeCountFixture(
            articleId = articleId,
            likeCount = likeCount,
        )

        assertThat(articleLikeCount.articleId).isEqualTo(articleId)
        assertThat(articleLikeCount.likeCount).isEqualTo(likeCount)
    }
}
