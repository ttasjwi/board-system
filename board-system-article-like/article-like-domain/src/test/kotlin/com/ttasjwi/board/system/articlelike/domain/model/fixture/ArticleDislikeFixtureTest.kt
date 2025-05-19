package com.ttasjwi.board.system.articlelike.domain.model.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleDislikeFixture 테스트")
class ArticleDislikeFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가진다.")
    fun test1() {
        val articleDislike = articleDislikeFixture()

        assertThat(articleDislike.articleDislikeId).isNotNull()
        assertThat(articleDislike.articleId).isNotNull()
        assertThat(articleDislike.userId).isNotNull()
        assertThat(articleDislike.createdAt).isNotNull()
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val articleDislikeId = 231341341L
        val articleId = 14578L
        val userId = 1345153135L
        val createdAt = appDateTimeFixture(minute = 12)

        val articleDislike = articleDislikeFixture(
            articleDislikeId = articleDislikeId,
            articleId = articleId,
            userId = userId,
            createdAt = createdAt,
        )

        assertThat(articleDislike.articleDislikeId).isEqualTo(articleDislikeId)
        assertThat(articleDislike.articleId).isEqualTo(articleId)
        assertThat(articleDislike.userId).isEqualTo(userId)
        assertThat(articleDislike.createdAt).isEqualTo(createdAt)
    }
}
