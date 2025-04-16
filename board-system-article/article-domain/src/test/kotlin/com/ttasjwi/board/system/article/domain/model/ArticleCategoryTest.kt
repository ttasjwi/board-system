package com.ttasjwi.board.system.article.domain.model

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ArticleCategory: 게시글 카테고리")
class ArticleCategoryTest {

    @Test
    @DisplayName("restore: 값을 통해 객체를 복원할 수 있다.")
    fun restore() {
        val articleCategoryId = 123434L
        val boardId = 13L
        val allowSelfDelete = true
        val articleCategory = ArticleCategory.restore(
            articleCategoryId = articleCategoryId,
            boardId = boardId,
            allowSelfDelete = allowSelfDelete,
        )

        assertThat(articleCategory.articleCategoryId).isEqualTo(articleCategoryId)
        assertThat(articleCategory.boardId).isEqualTo(boardId)
        assertThat(articleCategory.allowSelfDelete).isEqualTo(allowSelfDelete)
    }

    @Test
    @DisplayName("toString(): 디버깅용 문자열 반환")
    fun toStringTest() {
        val articleCategoryId = 123434L
        val boardId = 13L
        val allowSelfDelete = true
        val articleCategory = ArticleCategory.restore(
            articleCategoryId = articleCategoryId,
            boardId = boardId,
            allowSelfDelete = allowSelfDelete,
        )

        val str = articleCategory.toString()

        assertThat(str).isEqualTo(
            "ArticleCategory(articleCategoryId=$articleCategoryId, boardId=$boardId, allowSelfDelete=$allowSelfDelete)"
        )
    }
}
