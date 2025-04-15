package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BoardArticleCategory: 게시글 카테고리")
class BoardArticleCategoryTest {

    @Test
    @DisplayName("create: 값을 통해 객체를 생성할 수 있다.")
    fun createTest() {
        val boardArticleCategoryId = 123434L
        val name = "일반"
        val slug = "general"
        val boardId = 13L
        val allowSelfDelete = true
        val allowLike = false
        val allowDislike = false
        val createdAt = appDateTimeFixture(minute = 12)
        val boardArticleCategory = BoardArticleCategory.create(
            boardArticleCategoryId = boardArticleCategoryId,
            name = name,
            slug = slug,
            boardId = boardId,
            allowSelfDelete = allowSelfDelete,
            allowLike = allowLike,
            allowDislike = allowDislike,
            createdAt = createdAt,
        )

        assertThat(boardArticleCategory.boardArticleCategoryId).isEqualTo(boardArticleCategoryId)
        assertThat(boardArticleCategory.name).isEqualTo(name)
        assertThat(boardArticleCategory.slug).isEqualTo(slug)
        assertThat(boardArticleCategory.boardId).isEqualTo(boardId)
        assertThat(boardArticleCategory.allowSelfDelete).isEqualTo(allowSelfDelete)
        assertThat(boardArticleCategory.allowLike).isEqualTo(allowLike)
        assertThat(boardArticleCategory.allowDislike).isEqualTo(allowDislike)
        assertThat(boardArticleCategory.createdAt).isEqualTo(createdAt)
    }

    @Test
    @DisplayName("restore: 값을 통해 객체를 복원할 수 있다.")
    fun restore() {
        val boardArticleCategoryId = 123434L
        val name = "일반"
        val slug = "general"
        val boardId = 13L
        val allowSelfDelete = true
        val allowLike = false
        val allowDislike = false
        val createdAt = appDateTimeFixture(minute = 12).toLocalDateTime()
        val boardArticleCategory = BoardArticleCategory.restore(
            boardArticleCategoryId = boardArticleCategoryId,
            name = name,
            slug = slug,
            boardId = boardId,
            allowSelfDelete = allowSelfDelete,
            allowLike = allowLike,
            allowDislike = allowDislike,
            createdAt = createdAt,
        )

        assertThat(boardArticleCategory.boardArticleCategoryId).isEqualTo(boardArticleCategoryId)
        assertThat(boardArticleCategory.name).isEqualTo(name)
        assertThat(boardArticleCategory.slug).isEqualTo(slug)
        assertThat(boardArticleCategory.boardId).isEqualTo(boardId)
        assertThat(boardArticleCategory.allowSelfDelete).isEqualTo(allowSelfDelete)
        assertThat(boardArticleCategory.allowLike).isEqualTo(allowLike)
        assertThat(boardArticleCategory.allowDislike).isEqualTo(allowDislike)
        assertThat(boardArticleCategory.createdAt.toLocalDateTime()).isEqualTo(createdAt)
    }


    @Test
    @DisplayName("toString(): 디버깅용 문자열 반환")
    fun toStringTest() {
        val boardArticleCategoryId = 123434L
        val name = "일반"
        val slug = "general"
        val boardId = 13L
        val allowSelfDelete = true
        val allowLike = false
        val allowDislike = false
        val createdAt = appDateTimeFixture(minute = 12)
        val boardArticleCategory = BoardArticleCategory.create(
            boardArticleCategoryId = boardArticleCategoryId,
            name = name,
            slug = slug,
            boardId = boardId,
            allowSelfDelete = allowSelfDelete,
            allowLike = allowLike,
            allowDislike = allowDislike,
            createdAt = createdAt,
        )

        val str = boardArticleCategory.toString()

        assertThat(str).isEqualTo(
            "BoardArticleCategory(boardArticleCategoryId=$boardArticleCategoryId, boardId=$boardId, slug='$slug', createdAt=$createdAt, name='$name', allowSelfDelete=$allowSelfDelete, allowLike=$allowLike, allowDislike=$allowDislike)"
        )
    }
}
