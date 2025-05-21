package com.ttasjwi.board.system.articlelike.domain.model

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeCountFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleLikeCount 테스트")
class ArticleLikeCountTest {

    @Test
    @DisplayName("restore: 값으로 부터 복원")
    fun restoreTest() {
        // given
        val articleId = 12243414L
        val likeCount = 1314L

        // when
        val articleLikeCount = ArticleLikeCount.restore(
            articleId = articleId,
            likeCount = likeCount,
        )

        // then
        assertThat(articleLikeCount.articleId).isEqualTo(articleId)
        assertThat(articleLikeCount.likeCount).isEqualTo(likeCount)
    }

    @Test
    @DisplayName("toString(): 디버깅용 문자열 반환")
    fun toStringTest() {
        // given
        val articleId = 12243414L
        val likeCount = 1314L

        // when
        val articleLikeCount = articleLikeCountFixture(
            articleId = articleId,
            likeCount = likeCount,
        )

        assertThat(articleLikeCount.toString()).isEqualTo("ArticleLikeCount(articleId=$articleId, likeCount=$likeCount)")
    }
}
