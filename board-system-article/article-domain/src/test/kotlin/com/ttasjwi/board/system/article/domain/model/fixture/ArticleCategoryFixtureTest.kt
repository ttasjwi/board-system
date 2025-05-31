package com.ttasjwi.board.system.article.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-domain] ArticleCategoryFixture 테스트")
class ArticleCategoryFixtureTest {

    @Test
    @DisplayName("인자 없이 생성할 수 있다.")
    fun test1() {
        val articleCategory = articleCategoryFixture()

        assertThat(articleCategory).isNotNull
    }

    @Test
    @DisplayName("커스텀 파라미터 테스트")
    fun test2() {
        val articleCategoryId = 123434L
        val allowWrite = true
        val boardId = 13L
        val allowSelfEditDelete = true
        val articleCategory = articleCategoryFixture(
            articleCategoryId = articleCategoryId,
            boardId = boardId,
            allowWrite = allowWrite,
            allowSelfEditDelete = allowSelfEditDelete,
        )

        assertThat(articleCategory.articleCategoryId).isEqualTo(articleCategoryId)
        assertThat(articleCategory.boardId).isEqualTo(boardId)
        assertThat(articleCategory.allowWrite).isEqualTo(allowWrite)
        assertThat(articleCategory.allowSelfEditDelete).isEqualTo(allowSelfEditDelete)
    }
}
