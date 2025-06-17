package com.ttasjwi.board.system.article.infra.persistence

import com.ttasjwi.board.system.test.DataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-database-adapter] BoardArticleCountPersistenceAdapter 테스트")
class BoardArticleCountPersistenceAdapterTest : DataBaseIntegrationTest() {

    @Nested
    @DisplayName("increase: 게시판 게시글 수 증가")
    inner class IncreaseTest {

        @Test
        @DisplayName("최초 게시판 게시글 수 증가 테스트")
        fun test1() {
            // given
            val boardId = 5857L

            // when
            articleBoardArticleCountPersistenceAdapter.increase(boardId)


            // then
            val boardArticleCount = articleBoardArticleCountPersistenceAdapter.findByIdOrNull(boardId)!!

            assertThat(boardArticleCount.boardId).isEqualTo(boardId)
            assertThat(boardArticleCount.articleCount).isEqualTo(1)
        }

        @Test
        @DisplayName("첫번째 이후 게시판 게시글 수 증가 테스트")
        fun test2() {
            // given
            val boardId = 5857L

            // when
            articleBoardArticleCountPersistenceAdapter.increase(boardId)
            articleBoardArticleCountPersistenceAdapter.increase(boardId)

            // then
            val boardArticleCount = articleBoardArticleCountPersistenceAdapter.findByIdOrNull(boardId)!!

            assertThat(boardArticleCount.boardId).isEqualTo(boardId)
            assertThat(boardArticleCount.articleCount).isEqualTo(2)
        }
    }


    @Nested
    @DisplayName("decrease: 게시판 게시글 수 감소")
    inner class DecreaseTest {

        @Test
        @DisplayName("두번 게시판 게시글 수 증가 후, 게시글 수 감소 테스트")
        fun test() {
            // given
            val boardId = 5857L

            // when
            articleBoardArticleCountPersistenceAdapter.increase(boardId)
            articleBoardArticleCountPersistenceAdapter.increase(boardId)
            articleBoardArticleCountPersistenceAdapter.decrease(boardId)

            // then
            val boardArticleCount = articleBoardArticleCountPersistenceAdapter.findByIdOrNull(boardId)!!

            assertThat(boardArticleCount.boardId).isEqualTo(boardId)
            assertThat(boardArticleCount.articleCount).isEqualTo(1)
        }

    }


    @Nested
    @DisplayName("findByIdOrNull : boardId 값으로 게시판 게시글 수 조회")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("조회 실패시 null 반환 테스트")
        fun test2() {
            // given
            val boardId = 15555L

            // when
            // then
            val findBoardArticleCount = articleBoardArticleCountPersistenceAdapter.findByIdOrNull(boardId)
            assertThat(findBoardArticleCount).isNull()
        }
    }
}
