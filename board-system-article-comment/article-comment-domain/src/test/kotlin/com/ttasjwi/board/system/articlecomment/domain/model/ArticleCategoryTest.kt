package com.ttasjwi.board.system.articlecomment.domain.model

import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleCategoryFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] ArticleCategory 테스트")
class ArticleCategoryTest {

    @Test
    @DisplayName("restore: 값으로 부터 복원")
    fun restoreTest() {
        val articleCategoryId = 123434L
        val allowComment = false

        val articleCategory = ArticleCategory.restore(
            articleCategoryId = articleCategoryId,
            allowComment = allowComment,
        )
        assertThat(articleCategory.articleCategoryId).isEqualTo(articleCategoryId)
        assertThat(articleCategory.allowComment).isEqualTo(allowComment)
    }

    @Test
    @DisplayName("toString(): 디버깅 문자열 반환")
    fun toStringTest() {
        val articleCategoryId = 123434L
        val allowComment = false

        val articleCategory = articleCategoryFixture(
            articleCategoryId = articleCategoryId,
            allowComment = allowComment,
        )

        assertThat(articleCategory.toString()).isEqualTo(
            "ArticleCategory(articleCategoryId=$articleCategoryId, allowComment=$allowComment)"
        )
    }
}
