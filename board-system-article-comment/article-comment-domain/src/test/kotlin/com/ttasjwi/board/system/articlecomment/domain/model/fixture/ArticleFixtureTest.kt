package com.ttasjwi.board.system.articlecomment.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] ArticleFixture 테스트")
class ArticleFixtureTest {

    @Test
    @DisplayName("기본값 생성 테스트")
    fun test1() {
        // given
        // when
        val article = articleFixture()

        // then
        assertThat(article.articleId).isNotNull()
        assertThat(article.writerId).isNotNull()
        assertThat(article.articleCategoryId).isNotNull()
    }

    @Test
    @DisplayName("커스텀 값 지정 생성 테스트")
    fun test2() {
        // given
        val articleId = 1234L
        val writerId = 1333515L
        val articleCategoryId = 12444444L

        // when
        val article = articleFixture(
            articleId = articleId,
            writerId = writerId,
            articleCategoryId = articleCategoryId,
        )

        // then
        assertThat(article.articleId).isEqualTo(articleId)
        assertThat(article.writerId).isEqualTo(writerId)
        assertThat(article.articleCategoryId).isEqualTo(articleCategoryId)
    }
}
