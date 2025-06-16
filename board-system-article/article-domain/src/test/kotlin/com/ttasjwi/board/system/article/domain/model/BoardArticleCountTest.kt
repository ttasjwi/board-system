package com.ttasjwi.board.system.article.domain.model

import com.ttasjwi.board.system.article.domain.model.fixture.boardArticleCountFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-domain] BoardArticleCount: 게시판 게시글 수")
class BoardArticleCountTest {


    @Test
    @DisplayName("restore : 값으로 부터 객체 복원")
    fun restoreTest() {
        // given
        val boardId = 134L
        val articleCount = 55L

        // when
        val boardArticleCount = BoardArticleCount.restore(
            boardId = boardId,
            articleCount = articleCount
        )

        // then
        assertThat(boardArticleCount.boardId).isEqualTo(boardId)
        assertThat(boardArticleCount.articleCount).isEqualTo(articleCount)
    }

    @Test
    @DisplayName("toString() : 디버깅문자열 반환")
    fun toStringTest() {
        // given
        val boardId = 134L
        val articleCount = 55L

        // when
        val boardArticleCount = boardArticleCountFixture(
            boardId = boardId,
            articleCount = articleCount
        )

        // then
        assertThat(boardArticleCount.toString()).isEqualTo("BoardArticleCount(boardId=$boardId, articleCount=$articleCount)")
    }
}
