package com.ttasjwi.board.system.articlelike.domain.model

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleDislikeFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeFixture
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleDislike 테스트")
class ArticleDislikeTest {

    @Test
    @DisplayName("create : 게시글 싫어요 생성")
    fun createTest() {
        val articleDislikeId = 231341341L
        val articleId = 14578L
        val userId = 1345153135L
        val currentTime = appDateTimeFixture(minute = 12)

        val articleDislike = ArticleDislike.create(
            articleDislikeId = articleDislikeId,
            articleId = articleId,
            userId = userId,
            currentTime = currentTime,
        )

        assertThat(articleDislike.articleDislikeId).isEqualTo(articleDislikeId)
        assertThat(articleDislike.articleId).isEqualTo(articleId)
        assertThat(articleDislike.userId).isEqualTo(userId)
        assertThat(articleDislike.createdAt).isEqualTo(currentTime)
    }

    @Test
    @DisplayName("restore : 값으로 부터 복원")
    fun testRestore() {
        val articleDislikeId = 231341341L
        val articleId = 14578L
        val userId = 1345153135L
        val createdAt = appDateTimeFixture(minute = 12).toLocalDateTime()

        val articleDislike = ArticleDislike.restore(
            articleDislikeId = articleDislikeId,
            articleId = articleId,
            userId = userId,
            createdAt = createdAt,
        )

        assertThat(articleDislike.articleDislikeId).isEqualTo(articleDislikeId)
        assertThat(articleDislike.articleId).isEqualTo(articleId)
        assertThat(articleDislike.userId).isEqualTo(userId)
        assertThat(articleDislike.createdAt).isEqualTo(AppDateTime.from(createdAt))
    }


    @Test
    @DisplayName("toString() : 디버깅용 문자열 반환")
    fun toStringTest() {
        val articleDislikeId = 231341341L
        val articleId = 14578L
        val userId = 1345153135L
        val createdAt = appDateTimeFixture(minute = 12)

        val articleLike = articleDislikeFixture(
            articleDislikeId = articleDislikeId,
            articleId = articleId,
            userId = userId,
            createdAt = createdAt,
        )

        assertThat(articleLike.toString()).isEqualTo(
            "ArticleDislike(articleDislikeId=$articleDislikeId, articleId=$articleId, userId=$userId, createdAt=$createdAt)"
        )
    }
}
