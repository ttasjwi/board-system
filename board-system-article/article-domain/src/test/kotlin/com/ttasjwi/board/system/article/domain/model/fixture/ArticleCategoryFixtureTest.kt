package com.ttasjwi.board.system.article.domain.model.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ArticleCategoryFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가진다.")
    fun test1() {
        val articleCategory = articleCategoryFixture()

        assertThat(articleCategory.articleCategoryId).isNotNull
        assertThat(articleCategory.boardId).isNotNull
        assertThat(articleCategory.allowSelfDelete).isNotNull
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val articleCategoryId = 123434L
        val boardId = 13L
        val allowSelfDelete = true
        val articleCategory = articleCategoryFixture(
            articleCategoryId = articleCategoryId,
            boardId = boardId,
            allowSelfDelete = allowSelfDelete,
        )

        assertThat(articleCategory.articleCategoryId).isEqualTo(articleCategoryId)
        assertThat(articleCategory.boardId).isEqualTo(boardId)
        assertThat(articleCategory.allowSelfDelete).isEqualTo(allowSelfDelete)
    }
}
