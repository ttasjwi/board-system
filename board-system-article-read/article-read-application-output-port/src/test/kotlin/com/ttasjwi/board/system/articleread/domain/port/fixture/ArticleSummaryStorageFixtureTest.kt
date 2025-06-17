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
            assertThat(findArticleSummary.articleId).isEqualTo(articleSummary.articleId)
            assertThat(findArticleSummary.title).isEqualTo(articleSummary.title)
            assertThat(findArticleSummary.articleCategoryId).isEqualTo(articleSummary.articleCategoryId)
            assertThat(findArticleSummary.articleCategoryName).isEqualTo(articleSummary.articleCategoryName)
            assertThat(findArticleSummary.articleCategorySlug).isEqualTo(articleSummary.articleCategorySlug)
            assertThat(findArticleSummary.boardId).isEqualTo(articleSummary.boardId)
            assertThat(findArticleSummary.boardName).isEqualTo(articleSummary.boardName)
            assertThat(findArticleSummary.boardSlug).isEqualTo(articleSummary.boardSlug)
            assertThat(findArticleSummary.writerId).isEqualTo(articleSummary.writerId)
            assertThat(findArticleSummary.writerNickname).isEqualTo(articleSummary.writerNickname)
            assertThat(findArticleSummary.commentCount).isEqualTo(articleSummary.commentCount)
            assertThat(findArticleSummary.likeCount).isEqualTo(articleSummary.likeCount)
            assertThat(findArticleSummary.dislikeCount).isEqualTo(articleSummary.dislikeCount)
            assertThat(findArticleSummary.createdAt).isEqualTo(articleSummary.createdAt)
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
    @DisplayName("findAllPage: Offset 부터 시작하여, limit 만큼의 게시글 정보를 가져온다.")
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
                offset = 3,
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
                offset = 3,
                limit = 3,
            )

            // then
            assertThat(articles).isEmpty()
        }
    }

    @Nested
    @DisplayName("findAllInfiniteScroll : 게시글 무한 스크롤 조회")
    inner class FindAllByInfiniteScrollTest {

        @Test
        @DisplayName("lastArticleId 가 null 이면 게시글을 처음부터 limit 건 조회한다.")
        fun lastArticleIdNullTest() {
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

            // when
            val articles = articleSummaryStorageFixture.findAllInfiniteScroll(
                boardId = boardId,
                limit = 3,
                lastArticleId = null
            )

            // then
            val articleIds = articles.map { it.articleId }

            assertThat(articleIds.size).isEqualTo(3)
            assertThat(articleIds).containsExactly(10, 9, 8)
        }

        @Test
        @DisplayName("lastArticleId 가 있다면 해당 게시글 바로 이전부터, limit 건 조회한다.")
        fun lastArticleIdNotNullTest() {
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

            // when
            val articles = articleSummaryStorageFixture.findAllInfiniteScroll(
                boardId = boardId,
                limit = 3,
                lastArticleId = 8
            )

            // then
            val articleIds = articles.map { it.articleId }

            assertThat(articleIds.size).isEqualTo(3)
            assertThat(articleIds).containsExactly(7, 6, 5)
        }
    }
}
