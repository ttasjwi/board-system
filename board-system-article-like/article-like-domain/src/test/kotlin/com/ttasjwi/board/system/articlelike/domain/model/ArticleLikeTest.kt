package com.ttasjwi.board.system.articlelike.domain.model

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeFixture
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleLike 테스트")
class ArticleLikeTest {

    @Test
    @DisplayName("create : 게시글 좋아요 생성")
    fun createTest() {
        val articleLikeId = 231341341L
        val articleId = 14578L
        val userId = 1345153135L
        val currentTime = appDateTimeFixture(minute = 12)

        val articleLike = ArticleLike.create(
            articleLikeId = articleLikeId,
            articleId = articleId,
            userId = userId,
            currentTime = currentTime,
        )

        assertThat(articleLike.articleLikeId).isEqualTo(articleLikeId)
        assertThat(articleLike.articleId).isEqualTo(articleId)
        assertThat(articleLike.userId).isEqualTo(userId)
        assertThat(articleLike.createdAt).isEqualTo(currentTime)
    }

    @Test
    @DisplayName("restore : 값으로 부터 복원")
    fun testRestore() {
        val articleLikeId = 231341341L
        val articleId = 14578L
        val userId = 1345153135L
        val createdAt = appDateTimeFixture(minute = 12).toLocalDateTime()

        val articleLike = ArticleLike.restore(
            articleLikeId = articleLikeId,
            articleId = articleId,
            userId = userId,
            createdAt = createdAt,
        )

        assertThat(articleLike.articleLikeId).isEqualTo(articleLikeId)
        assertThat(articleLike.articleId).isEqualTo(articleId)
        assertThat(articleLike.userId).isEqualTo(userId)
        assertThat(articleLike.createdAt).isEqualTo(AppDateTime.from(createdAt))
    }


    @Test
    @DisplayName("toString() : 디버깅용 문자열 반환")
    fun toStringTest() {
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

        assertThat(articleLike.toString()).isEqualTo(
            "ArticleLike(articleLikeId=$articleLikeId, articleId=$articleId, userId=$userId, createdAt=$createdAt)"
        )
    }
}
