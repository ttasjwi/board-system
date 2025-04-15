package com.ttasjwi.board.system.board.domain.model.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BoardArticleCategory 픽스쳐 테스트")
class BoardArticleCategoryFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가진다.")
    fun test1() {
        val boardArticleCategory = boardArticleCategoryFixture()

        assertThat(boardArticleCategory.boardArticleCategoryId).isNotNull
        assertThat(boardArticleCategory.name).isNotNull
        assertThat(boardArticleCategory.slug).isNotNull
        assertThat(boardArticleCategory.boardId).isNotNull
        assertThat(boardArticleCategory.allowSelfDelete).isNotNull
        assertThat(boardArticleCategory.allowLike).isNotNull
        assertThat(boardArticleCategory.allowDislike).isNotNull
        assertThat(boardArticleCategory.createdAt).isNotNull
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val boardArticleCategoryId = 123434L
        val name = "일반"
        val slug = "general"
        val boardId = 13L
        val allowSelfDelete = true
        val allowLike = false
        val allowDislike = false
        val createdAt = appDateTimeFixture(minute = 12)
        val boardArticleCategory = boardArticleCategoryFixture(
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
}
