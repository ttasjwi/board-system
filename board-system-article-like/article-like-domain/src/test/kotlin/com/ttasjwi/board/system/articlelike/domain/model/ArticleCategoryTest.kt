package com.ttasjwi.board.system.articlelike.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleCategory 테스트")
class ArticleCategoryTest {

    @Test
    @DisplayName("restore: 값으로 부터 복원")
    fun restoreTest() {
        val articleCategoryId = 123434L
        val allowLike = false
        val allowDislike = false

        val articleCategory = ArticleCategory.restore(
            articleCategoryId = articleCategoryId,
            allowLike = allowLike,
            allowDislike = allowDislike,
        )
        assertThat(articleCategory.articleCategoryId).isEqualTo(articleCategoryId)
        assertThat(articleCategory.allowLike).isEqualTo(allowLike)
        assertThat(articleCategory.allowDislike).isEqualTo(allowDislike)
    }

    @Test
    @DisplayName("toString(): 디버깅 문자열 반환")
    fun toStringTest() {
        val articleCategoryId = 123434L
        val allowLike = false
        val allowDislike = false

        val articleCategory = ArticleCategory.restore(
            articleCategoryId = articleCategoryId,
            allowLike = allowLike,
            allowDislike = allowDislike,
        )

        assertThat(articleCategory.toString()).isEqualTo(
            "ArticleCategory(articleCategoryId=$articleCategoryId, allowLike=$allowLike, allowDislike=$allowDislike)"
        )
    }
}
