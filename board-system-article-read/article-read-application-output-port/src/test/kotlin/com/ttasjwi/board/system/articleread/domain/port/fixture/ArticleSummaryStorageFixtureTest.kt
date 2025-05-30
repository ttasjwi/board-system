package com.ttasjwi.board.system.articleread.domain.port.fixture

import com.ttasjwi.board.system.articleread.domain.model.fixture.articleSummaryQueryModelFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-read-application-output-port] ArticleSummaryStorageFixture 테스트")
class ArticleSummaryStorageFixtureTest {

    private lateinit var articleSummaryStorageFixture: ArticleSummaryStorageFixture

    @BeforeEach
    fun setup() {
        articleSummaryStorageFixture = ArticleSummaryStorageFixture()
    }

    @Nested
    @DisplayName("findByIdOrNullTest : 저장 후 식별자 조회 테스트")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("대상이 있을 경우 조회 성공")
        fun test1() {
            // given
            val articleSummary = articleSummaryQueryModelFixture()
            articleSummaryStorageFixture.save(articleSummary)

            // when
            val findArticleSummary = articleSummaryStorageFixture.findByIdOrNull(articleSummary.articleId)!!

            // then
            assertThat(findArticleSummary.articleId).isEqualTo(articleSummary.articleId)

            // then
            assertThat(findArticleSummary.articleId).isEqualTo(findArticleSummary.articleId)
            assertThat(findArticleSummary.title).isEqualTo(findArticleSummary.title)
            assertThat(findArticleSummary.articleCategory.articleCategoryId).isEqualTo(findArticleSummary.articleCategory.articleCategoryId)
            assertThat(findArticleSummary.articleCategory.name).isEqualTo(findArticleSummary.articleCategory.name)
            assertThat(findArticleSummary.articleCategory.slug).isEqualTo(findArticleSummary.articleCategory.slug)
            assertThat(findArticleSummary.board.boardId).isEqualTo(findArticleSummary.board.boardId)
            assertThat(findArticleSummary.board.name).isEqualTo(findArticleSummary.board.name)
            assertThat(findArticleSummary.board.slug).isEqualTo(findArticleSummary.board.slug)
            assertThat(findArticleSummary.writer.writerId).isEqualTo(findArticleSummary.writer.writerId)
            assertThat(findArticleSummary.writer.nickname).isEqualTo(findArticleSummary.writer.nickname)
            assertThat(findArticleSummary.commentCount).isEqualTo(findArticleSummary.commentCount)
            assertThat(findArticleSummary.likeCount).isEqualTo(findArticleSummary.likeCount)
            assertThat(findArticleSummary.dislikeCount).isEqualTo(findArticleSummary.dislikeCount)
            assertThat(findArticleSummary.createdAt).isEqualTo(findArticleSummary.createdAt)
        }


        @Test
        @DisplayName("대상이 없을 경우 null 반환")
        fun test2() {
            // given
            val articleId = 145L

            // when
            val articleSummary = articleSummaryStorageFixture.findByIdOrNull(articleId)

            // then
            assertThat(articleSummary).isNull()
        }
    }

    @Nested
    @DisplayName("findAllPage: OffSet부터 시작하여, limit 만큼의 게시글 정보를 가져온다.")
    inner class FindAllPageTest {

        @Test
        @DisplayName("성공 테스트")
        fun test1() {
            // given
            val boardId = 1234566L

            for (i in 1..10) {
                val article = articleSummaryQueryModelFixture(
                    articleId = i.toLong(),
                    boardId = boardId,
                    title = "title-$i",
                )
                articleSummaryStorageFixture.save(article)
            }

            // 10 9 8 / 7 6 ... 1
            // OffSet 을 3 부터 잡고, pageSize 를 3으로 잡기
            // 7, 6, 5
            val articles = articleSummaryStorageFixture.findAllPage(
                boardId = boardId,
                offSet = 3,
                limit = 3,
            )
            val articleIds = articles.map { it.articleId }

            assertThat(articleIds.size).isEqualTo(3)
            assertThat(articleIds).containsExactly(7, 6, 5)
        }


        @Test
        @DisplayName("조회대상을 찾지 못 했을 경우, 빈 리스트가 반환된다.")
        fun test2() {
            // given
            // when
            val articles = articleSummaryStorageFixture.findAllPage(
                boardId = 88949457L,
                offSet = 3,
                limit = 3,
            )

            // then
            assertThat(articles).isEmpty()
        }
    }

    @Nested
    @DisplayName("count: 최대 limit 건까지 범위 내에서 게시판 게시글의 갯수를 센다.")
    inner class CountTest {


        @Test
        @DisplayName("게시글 갯수가 limit 보다 같거나, 많으면, limit 만큼 갯수를 센다.")
        fun test1() {
            // given
            val boardId = 1234566L
            for (i in 1L..10L) {
                articleSummaryStorageFixture.save(
                    articleSummaryQueryModelFixture(
                        articleId = i,
                        boardId = boardId
                    )
                )
            }

            // when
            val count = articleSummaryStorageFixture.count(boardId, 9)

            // then
            assertThat(count).isEqualTo(9)
        }


        @Test
        @DisplayName("게시글 갯수가 limit 보다 적으면 게시글 갯수까지만큼 센다.")
        fun test2() {
            // given
            val boardId = 1234566L
            for (i in 1L..10L) {
                articleSummaryStorageFixture.save(
                    articleSummaryQueryModelFixture(
                        articleId = i,
                        boardId = boardId
                    )
                )
            }

            // when
            val count = articleSummaryStorageFixture.count(boardId, 15)

            // then
            assertThat(count).isEqualTo(10)
        }
    }
}
