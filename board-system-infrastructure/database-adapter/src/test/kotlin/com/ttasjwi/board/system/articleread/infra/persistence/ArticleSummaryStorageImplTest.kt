package com.ttasjwi.board.system.articleread.infra.persistence

import com.ttasjwi.board.system.articleread.infra.persistence.jpa.*
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.DataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-read-database-adapter] ArticleSummaryStorageImpl 테스트")
class ArticleSummaryStorageImplTest : DataBaseIntegrationTest() {

    @Nested
    @DisplayName("findAllPage: OffSet 부터 시작하여, pageSize 만큼의 게시글 정보를 가져온다.")
    inner class FindAllPageTest {

        @Test
        @DisplayName("페이징 대상이 의도한 대로 가져와지는 지 테스트")
        fun test1() {
            // given
            val boardId = 1234566L
            createBoard(boardId)

            for (i in 1..10) {
                createArticleCategory(
                    articleCategoryId = i.toLong(),
                    boardId = boardId,
                )
                createArticle(
                    articleId = i.toLong(),
                    articleCategoryId = i.toLong(),
                    boardId = boardId,
                    writerId = i.toLong(),
                    writerNickname = "writer$i",
                )
                createCommentCount(
                    articleId = i.toLong(),
                    commentCount = i.toLong(),
                )
                createLikeCount(
                    articleId = i.toLong(),
                    likeCount = i.toLong(),
                )
                createDislikeCount(
                    articleId = i.toLong(),
                    dislikeCount = i.toLong(),
                )
            }
            // 10 9 8 / 7 6 ... 1
            // OffSet 을 3 부터 잡고, limit 를 3으로 잡기
            // 7, 6, 5
            val articleSummaries = articleReadArticleSummaryStorage.findAllPage(
                boardId = boardId,
                offSet = 3,
                limit = 3,
            )
            for (articleSummary in articleSummaries) {
                println("articleSummary= $articleSummary")
            }

            val articleIds = articleSummaries.map { it.articleId }
            val boardNames = articleSummaries.map { it.board.name }
            val articleCategoryNames = articleSummaries.map { it.articleCategory.name }
            val commentCounts = articleSummaries.map { it.commentCount }
            val likeCounts = articleSummaries.map { it.likeCount }
            val dislikeCounts = articleSummaries.map { it.dislikeCount }

            assertThat(articleIds.size).isEqualTo(3)
            assertThat(articleIds).containsExactly(7, 6, 5)
            assertThat(boardNames).containsExactly("board/$boardId", "board/$boardId", "board/$boardId")
            assertThat(articleCategoryNames).containsExactly("category-7", "category-6", "category-5")
            assertThat(commentCounts).containsExactly(7, 6, 5)
            assertThat(likeCounts).containsExactly(7, 6, 5)
            assertThat(dislikeCounts).containsExactly(7, 6, 5)
        }


        @Test
        @DisplayName("댓글수/좋아요수/싫어요수가 저장되어 있지 않을 경우 0으로 가져와 지는 지 테스트")
        fun test2() {
            // given
            val boardId = 1234566L
            createBoard(boardId)

            for (i in 1..10) {
                createArticleCategory(
                    articleCategoryId = i.toLong(),
                    boardId = boardId,
                )
                createArticle(
                    articleId = i.toLong(),
                    articleCategoryId = i.toLong(),
                    boardId = boardId,
                    writerId = i.toLong(),
                    writerNickname = "writer$i",
                )
            }
            // 10 9 8 / 7 6 ... 1
            // OffSet 을 3 부터 잡고, limit 를 3으로 잡기
            // 7, 6, 5
            val articleSummaries = articleReadArticleSummaryStorage.findAllPage(
                boardId = boardId,
                offSet = 3,
                limit = 3,
            )
            for (articleSummary in articleSummaries) {
                println("articleSummary= $articleSummary")
            }

            val articleIds = articleSummaries.map { it.articleId }
            val boardNames = articleSummaries.map { it.board.name }
            val articleCategoryNames = articleSummaries.map { it.articleCategory.name }
            val commentCounts = articleSummaries.map { it.commentCount }
            val likeCounts = articleSummaries.map { it.likeCount }
            val dislikeCounts = articleSummaries.map { it.dislikeCount }

            assertThat(articleIds.size).isEqualTo(3)
            assertThat(articleIds).containsExactly(7, 6, 5)
            assertThat(boardNames).containsExactly("board/$boardId", "board/$boardId", "board/$boardId")
            assertThat(articleCategoryNames).containsExactly("category-7", "category-6", "category-5")
            assertThat(commentCounts).containsExactly(0, 0, 0)
            assertThat(likeCounts).containsExactly(0, 0, 0)
            assertThat(dislikeCounts).containsExactly(0, 0, 0)
        }

        @Test
        @DisplayName("조회대상을 찾지 못 했을 경우, 빈 리스트가 반환된다.")
        fun test3() {
            // given
            // when
            val articles = articleReadArticleSummaryStorage.findAllPage(
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
            for (i in 1..10) {
                createArticle(
                    articleId = i.toLong(),
                    articleCategoryId = i.toLong(),
                    boardId = boardId,
                    writerId = i.toLong(),
                    writerNickname = "writer$i",
                )
            }

            // when
            val count = articleReadArticleSummaryStorage.count(boardId, 9)

            // then
            assertThat(count).isEqualTo(9)
        }


        @Test
        @DisplayName("게시글 갯수가 limit 보다 적으면 게시글 갯수까지만큼 센다.")
        fun test2() {
            // given
            val boardId = 1234566L
            for (i in 1..10) {
                createArticle(
                    articleId = i.toLong(),
                    articleCategoryId = i.toLong(),
                    boardId = boardId,
                    writerId = i.toLong(),
                    writerNickname = "writer$i",
                )
            }

            // when
            val count = articleReadArticleSummaryStorage.count(boardId, 15)

            // then
            assertThat(count).isEqualTo(10)
        }
    }

    private fun createBoard(
        boardId: Long,
    ) {
        val board = JpaBoard(
            boardId = boardId,
            name = "board/$boardId",
            description = "게시판/$boardId",
            managerId = 1L,
            slug = "board$boardId",
            createdAt = appDateTimeFixture(minute = 0).toLocalDateTime()
        )
        entityManager.persist(board)
    }


    private fun createArticleCategory(
        articleCategoryId: Long,
        boardId: Long,
    ) {
        val articleCategory = JpaArticleCategory(
            articleCategoryId = articleCategoryId,
            boardId = boardId,
            name = "category-$articleCategoryId",
            slug = "slug$articleCategoryId",
            allowSelfDelete = true,
            allowLike = true,
            allowDislike = true,
            createdAt = appDateTimeFixture(minute = 0).toLocalDateTime()
        )
        entityManager.persist(articleCategory)
    }

    private fun createArticle(
        articleId: Long,
        boardId: Long,
        articleCategoryId: Long,
        writerId: Long,
        writerNickname: String,
    ) {
        val jpaArticle = JpaArticle(
            articleId = articleId,
            boardId = boardId,
            title = "title-$articleId",
            content = "content-$articleId",
            articleCategoryId = articleCategoryId,
            writerId = writerId,
            writerNickname = writerNickname,
            createdAt = appDateTimeFixture(minute = 0).toLocalDateTime(),
            modifiedAt = appDateTimeFixture(minute = 0).toLocalDateTime()
        )
        entityManager.persist(jpaArticle)
    }

    private fun createCommentCount(
        articleId: Long,
        commentCount: Long
    ) {
        val jpaArticleCommentCount = JpaArticleCommentCount(
            articleId = articleId,
            commentCount = commentCount
        )
        entityManager.persist(jpaArticleCommentCount)
    }

    private fun createLikeCount(
        articleId: Long,
        likeCount: Long
    ) {
        val articleLikeCount = JpaArticleLikeCount(
            articleId = articleId,
            likeCount = likeCount
        )
        entityManager.persist(articleLikeCount)
    }

    private fun createDislikeCount(
        articleId: Long,
        dislikeCount: Long
    ) {
        val articleDislikeCount = JpaArticleDislikeCount(
            articleId = articleId,
            dislikeCount = dislikeCount
        )
        entityManager.persist(articleDislikeCount)
    }
}
