package com.ttasjwi.board.system.articlelike.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleCategoryFixture 테스트")
class ArticleCategoryFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가진다.")
    fun test1() {
        val articleCategory = articleCategoryFixture()
        assertThat(articleCategory.articleCategoryId).isNotNull
        assertThat(articleCategory.allowLike).isNotNull
        assertThat(articleCategory.allowDislike).isNotNull
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val articleCategoryId = 123434L
        val allowLike = false
        val allowDislike = false
        val articleCategory = articleCategoryFixture(
            articleCategoryId = articleCategoryId,
            allowLike = allowLike,
            allowDislike = allowDislike,
        )
        assertThat(articleCategory.articleCategoryId).isEqualTo(articleCategoryId)
        assertThat(articleCategory.allowLike).isEqualTo(allowLike)
        assertThat(articleCategory.allowDislike).isEqualTo(allowDislike)
    }
}
