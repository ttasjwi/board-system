package com.ttasjwi.board.system.articlelike.domain.model

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleDislikeCountFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleDislikeCount 테스트")
class ArticleDislikeCountTest {

    @Test
    @DisplayName("restore: 값으로 부터 복원")
    fun restoreTest() {
        // given
        val articleId = 12243414L
        val dislikeCount = 1314L

        // when
        val articleDislikeCount = ArticleDislikeCount.restore(
            articleId = articleId,
            dislikeCount = dislikeCount,
        )

        // then
        assertThat(articleDislikeCount.articleId).isEqualTo(articleId)
        assertThat(articleDislikeCount.dislikeCount).isEqualTo(dislikeCount)
    }

    @Test
    @DisplayName("toString(): 디버깅용 문자열 반환")
    fun toStringTest() {
        // given
        val articleId = 12243414L
        val dislikeCount = 1314L

        // when
        val articleDislikeCount = articleDislikeCountFixture(
            articleId = articleId,
            dislikeCount = dislikeCount,
        )

        assertThat(articleDislikeCount.toString()).isEqualTo("ArticleDislikeCount(articleId=$articleId, dislikeCount=$dislikeCount)")
    }
}
