package com.ttasjwi.board.system.articlecomment.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] ArticleCategoryFixture 테스트")
class ArticleCategoryFixtureTest {

    @Test
    @DisplayName("디폴트 파라미터 생성 테스트")
    fun test1() {
        val articleCategory = articleCategoryFixture()
        assertThat(articleCategory.articleCategoryId).isNotNull
        assertThat(articleCategory.allowComment).isNotNull
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val articleCategoryId = 123434L
        val allowComment = false

        val articleCategory = articleCategoryFixture(
            articleCategoryId = articleCategoryId,
            allowComment = allowComment
        )
        assertThat(articleCategory.articleCategoryId).isEqualTo(articleCategoryId)
        assertThat(articleCategory.allowComment).isEqualTo(allowComment)
    }
}
