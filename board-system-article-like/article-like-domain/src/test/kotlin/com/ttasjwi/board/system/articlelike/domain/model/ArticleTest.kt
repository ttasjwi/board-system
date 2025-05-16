package com.ttasjwi.board.system.articlelike.domain.model

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] Article 테스트")
class ArticleTest {

    @Test
    @DisplayName("restore : 값으로부터 복원")
    fun restore() {
        val articleId = 14578L
        val articleCategoryId = 2314558888L

        val article = Article.restore(
            articleId = articleId,
            articleCategoryId = articleCategoryId,
        )

        assertThat(article.articleId).isEqualTo(articleId)
        assertThat(article.articleCategoryId).isEqualTo(articleCategoryId)
    }

    @Test
    @DisplayName("toString(): 디버깅용 문자열 반환")
    fun toStringTest() {
        val articleId = 14578L
        val articleCategoryId = 2314558888L

        val article = articleFixture(
            articleId = articleId,
            articleCategoryId = articleCategoryId,
        )

        assertThat(article.toString()).isEqualTo("Article(articleId=$articleId, articleCategoryId=$articleCategoryId)")
    }
}
