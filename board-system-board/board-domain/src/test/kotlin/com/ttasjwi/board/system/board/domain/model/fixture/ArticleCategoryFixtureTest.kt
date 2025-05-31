package com.ttasjwi.board.system.board.domain.model.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[board-domain] ArticleCategoryFixture 테스트")
class ArticleCategoryFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가진 채 생성된다.")
    fun test1() {
        val articleCategory = articleCategoryFixture()

        assertThat(articleCategory).isNotNull
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val articleCategoryId = 123434L
        val name = "일반"
        val slug = "general"
        val boardId = 13L
        val allowWrite = false
        val allowSelfEditDelete = true
        val allowComment = false
        val allowLike = false
        val allowDislike = false
        val createdAt = appDateTimeFixture(minute = 12)
        val articleCategory = articleCategoryFixture(
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
}
