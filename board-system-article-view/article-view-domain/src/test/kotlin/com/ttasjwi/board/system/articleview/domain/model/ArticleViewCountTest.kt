package com.ttasjwi.board.system.articleview.domain.model

import com.ttasjwi.board.system.articleview.domain.model.fixture.articleViewCountFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-view-domain] ArticleViewCount 테스트")
class ArticleViewCountTest {

    @Test
    @DisplayName("restore: 값으로 부터 복원")
    fun restoreTest() {
        // given
        val articleId = 12243414L
        val viewCount = 1314L

        // when
        val articleViewCount = ArticleViewCount.restore(
            articleId = articleId,
            viewCount = viewCount,
        )

        // then
        assertThat(articleViewCount.articleId).isEqualTo(articleId)
        assertThat(articleViewCount.viewCount).isEqualTo(viewCount)
    }

    @Test
    @DisplayName("toString(): 디버깅용 문자열 반환")
    fun toStringTest() {
        // given
        val articleId = 12243414L
        val viewCount = 1314L

        // when
        val articleViewCount = articleViewCountFixture(
            articleId = articleId,
            viewCount = viewCount,
        )

        assertThat(articleViewCount.toString()).isEqualTo("ArticleViewCount(articleId=$articleId, viewCount=$viewCount)")
    }
}
