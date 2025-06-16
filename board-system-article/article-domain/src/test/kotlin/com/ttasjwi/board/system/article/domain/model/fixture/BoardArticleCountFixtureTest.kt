package com.ttasjwi.board.system.article.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-domain] BoardArticleCountFixture 테스트")
class BoardArticleCountFixtureTest {

    @Test
    @DisplayName("기본값 생성 테스트")
    fun test1() {
        // given
        // when
        val boardArticleCount = boardArticleCountFixture()

        // then
        assertThat(boardArticleCount.boardId).isNotNull
        assertThat(boardArticleCount.articleCount).isNotNull
    }

    @Test
    @DisplayName("커스텀 값 지정 테스트")
    fun test2() {
        // given
        val boardId = 134L
        val articleCount = 55L

        // when
        val boardArticleCount = boardArticleCountFixture(
            boardId = boardId,
            articleCount = articleCount
        )

        // then
        assertThat(boardArticleCount.boardId).isEqualTo(boardId)
        assertThat(boardArticleCount.articleCount).isEqualTo(articleCount)
    }
}
