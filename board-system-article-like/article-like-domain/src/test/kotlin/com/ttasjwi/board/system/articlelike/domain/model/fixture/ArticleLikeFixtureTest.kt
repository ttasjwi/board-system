package com.ttasjwi.board.system.articlelike.domain.model.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleLikeFixture 테스트")
class ArticleLikeFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가진다.")
    fun test1() {
        val articleLike = articleLikeFixture()

        assertThat(articleLike.articleLikeId).isNotNull()
        assertThat(articleLike.articleId).isNotNull()
        assertThat(articleLike.userId).isNotNull()
        assertThat(articleLike.createdAt).isNotNull()
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val articleLikeId = 231341341L
        val articleId = 14578L
        val userId = 1345153135L
        val createdAt = appDateTimeFixture(minute = 12)

        val articleLike = articleLikeFixture(
            articleLikeId = articleLikeId,
            articleId = articleId,
            userId = userId,
            createdAt = createdAt,
        )

        assertThat(articleLike.articleLikeId).isEqualTo(articleLikeId)
        assertThat(articleLike.articleId).isEqualTo(articleId)
        assertThat(articleLike.userId).isEqualTo(userId)
        assertThat(articleLike.createdAt).isEqualTo(createdAt)
    }
}
