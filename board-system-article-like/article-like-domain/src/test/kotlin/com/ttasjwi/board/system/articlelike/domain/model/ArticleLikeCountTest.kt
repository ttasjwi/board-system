package com.ttasjwi.board.system.articlelike.domain.model

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeCountFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleLikeCount 테스트")
class ArticleLikeCountTest {


    @Test
    @DisplayName("init: 초기화")
    fun initTest() {
        // given
        val articleId = 12243414L

        // when
        val articleLikeCount = ArticleLikeCount.init(articleId)

        // then
        assertThat(articleLikeCount.articleId).isEqualTo(articleId)
        assertThat(articleLikeCount.likeCount).isEqualTo(0L)
    }


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
    @DisplayName("increase : 좋아요 수 증가")
    fun increaseTest() {
        // given
        val articleId = 12243414L
        val likeCount = 1314L
        val articleLikeCount = articleLikeCountFixture(
            articleId = articleId,
            likeCount = likeCount,
        )
        // when
        articleLikeCount.increase()

        // then
        assertThat(articleLikeCount.likeCount).isEqualTo(likeCount + 1)
    }


    @Test
    @DisplayName("decrease(): 좋아요 수 감소")
    fun decreaseTest() {
        // given
        val articleId = 12243414L
        val likeCount = 1314L
        val articleLikeCount = articleLikeCountFixture(
            articleId = articleId,
            likeCount = likeCount,
        )

        // when
        articleLikeCount.decrease()

        // then
        assertThat(articleLikeCount.likeCount).isEqualTo(likeCount - 1)
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
