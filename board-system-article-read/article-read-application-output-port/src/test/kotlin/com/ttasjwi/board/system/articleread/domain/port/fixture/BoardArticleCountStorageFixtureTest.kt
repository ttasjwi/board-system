package com.ttasjwi.board.system.articleread.domain.port.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-read-application-output-port] BoardArticleCountStorageFixture 테스트")
class BoardArticleCountStorageFixtureTest {

    private lateinit var boardArticleCountStorageFixture: BoardArticleCountStorageFixture

    @BeforeEach
    fun setup() {
        boardArticleCountStorageFixture = BoardArticleCountStorageFixture()
    }

    @Nested
    @DisplayName("count: 게시판의 게시글 수를 조회한다.")
    inner class CountTest {

        @Test
        @DisplayName("게시글 수 저장 후 조회 테스트")
        fun testSuccess() {
            // given
            val boardId = 34L
            val articleCount = 348L

            boardArticleCountStorageFixture.save(boardId, articleCount)

            // when
            val findArticleCount = boardArticleCountStorageFixture.count(boardId)

            // then
            assertThat(findArticleCount).isEqualTo(articleCount)
        }


        @Test
        @DisplayName("게시글 수가 저장되어 있지 않다면, 게시글 수 조회 시도시, 0이 반환된다.")
        fun testNotFound() {
            // given
            // when
            val findArticleCount = boardArticleCountStorageFixture.count(131415L)

            // then
            assertThat(findArticleCount).isEqualTo(0L)
        }
    }
}
