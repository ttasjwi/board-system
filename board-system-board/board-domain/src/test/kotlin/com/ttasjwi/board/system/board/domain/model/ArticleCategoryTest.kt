package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[board-domain] ArticleCategory: 게시글 카테고리")
class ArticleCategoryTest {

    @Test
    @DisplayName("create: 값을 통해 객체를 생성할 수 있다.")
    fun createTest() {
        val articleCategoryId = 123434L
        val name = "일반"
        val slug = "general"
        val boardId = 13L
        val allowWrite = true
        val allowSelfEditDelete = true
        val allowComment = true
        val allowLike = false
        val allowDislike = false
        val createdAt = appDateTimeFixture(minute = 12)
        val articleCategory = ArticleCategory.create(
            articleCategoryId = articleCategoryId,
            name = name,
            slug = slug,
            boardId = boardId,
            allowWrite = allowWrite,
            allowSelfEditDelete = allowSelfEditDelete,
            allowComment = allowComment,
            allowLike = allowLike,
            allowDislike = allowDislike,
            createdAt = createdAt,
        )

        assertThat(articleCategory.articleCategoryId).isEqualTo(articleCategoryId)
        assertThat(articleCategory.name).isEqualTo(name)
        assertThat(articleCategory.slug).isEqualTo(slug)
        assertThat(articleCategory.boardId).isEqualTo(boardId)
        assertThat(articleCategory.allowWrite).isEqualTo(allowWrite)
        assertThat(articleCategory.allowSelfEditDelete).isEqualTo(allowSelfEditDelete)
        assertThat(articleCategory.allowComment).isEqualTo(allowComment)
        assertThat(articleCategory.allowLike).isEqualTo(allowLike)
        assertThat(articleCategory.allowDislike).isEqualTo(allowDislike)
        assertThat(articleCategory.createdAt).isEqualTo(createdAt)
    }

    @Test
    @DisplayName("restore: 값을 통해 객체를 복원할 수 있다.")
    fun restore() {
        val articleCategoryId = 123434L
        val name = "일반"
        val slug = "general"
        val boardId = 13L
        val allowWrite = true
        val allowSelfEditDelete = true
        val allowComment = true
        val allowLike = false
        val allowDislike = false
        val createdAt = appDateTimeFixture(minute = 12).toLocalDateTime()
        val articleCategory = ArticleCategory.restore(
            articleCategoryId = articleCategoryId,
            name = name,
            slug = slug,
            boardId = boardId,
            allowWrite = allowWrite,
            allowSelfEditDelete = allowSelfEditDelete,
            allowComment = allowComment,
            allowLike = allowLike,
            allowDislike = allowDislike,
            createdAt = createdAt,
        )

        assertThat(articleCategory.articleCategoryId).isEqualTo(articleCategoryId)
        assertThat(articleCategory.name).isEqualTo(name)
        assertThat(articleCategory.slug).isEqualTo(slug)
        assertThat(articleCategory.boardId).isEqualTo(boardId)
        assertThat(articleCategory.allowWrite).isEqualTo(allowWrite)
        assertThat(articleCategory.allowSelfEditDelete).isEqualTo(allowSelfEditDelete)
        assertThat(articleCategory.allowComment).isEqualTo(allowComment)
        assertThat(articleCategory.allowLike).isEqualTo(allowLike)
        assertThat(articleCategory.allowDislike).isEqualTo(allowDislike)
        assertThat(articleCategory.createdAt.toLocalDateTime()).isEqualTo(createdAt)
    }


    @Test
    @DisplayName("toString(): 디버깅용 문자열 반환")
    fun toStringTest() {
        // given
        val articleCategoryId = 123434L
        val name = "일반"
        val slug = "general"
        val boardId = 13L
        val allowWrite = true
        val allowSelfEditDelete = true
        val allowComment = true
        val allowLike = false
        val allowDislike = false
        val createdAt = appDateTimeFixture(minute = 12)
        val articleCategory = ArticleCategory.create(
            articleCategoryId = articleCategoryId,
            name = name,
            slug = slug,
            boardId = boardId,
            allowWrite = allowWrite,
            allowSelfEditDelete = allowSelfEditDelete,
            allowComment = allowComment,
            allowLike = allowLike,
            allowDislike = allowDislike,
            createdAt = createdAt,
        )

        // when
        // then
        assertThat(articleCategory.toString()).isEqualTo("ArticleCategory(articleCategoryId=$articleCategoryId, name='$name', slug='$slug', boardId=$boardId, allowWrite=$allowWrite, allowSelfEditDelete=$allowSelfEditDelete, allowComment=$allowComment, allowLike=$allowLike, allowDislike=$allowDislike, createdAt=$createdAt)")
    }
}
