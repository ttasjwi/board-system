package com.ttasjwi.board.system.articlecomment.domain.port.fixture

import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleCommentFixture
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentPersistencePort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-application-output-port] ArticleCommentPersistencePortFixture 테스트")
class ArticleCommentPersistencePortFixtureTest {

    private lateinit var articleCommentPersistencePortFixture: ArticleCommentPersistencePort

    @BeforeEach
    fun setUp() {
        articleCommentPersistencePortFixture = ArticleCommentPersistencePortFixture()
    }

    @Nested
    @DisplayName("저장 후 식별자 조회 테스트")
    inner class SaveAndFindTest {


        @Test
        @DisplayName("조회 성공하는 경우")
        fun testSuccess() {
            // given
            val articleComment = articleCommentFixture()

            // when
            articleCommentPersistencePortFixture.save(articleComment)
            val findArticleComment = articleCommentPersistencePortFixture.findById(articleComment.articleCommentId)!!

            // then
            assertThat(findArticleComment.articleCommentId).isEqualTo(articleComment.articleCommentId)
            assertThat(findArticleComment.content).isEqualTo(articleComment.content)
            assertThat(findArticleComment.articleId).isEqualTo(articleComment.articleId)
            assertThat(findArticleComment.rootParentCommentId).isEqualTo(articleComment.rootParentCommentId)
            assertThat(findArticleComment.writerId).isEqualTo(articleComment.writerId)
            assertThat(findArticleComment.writerNickname).isEqualTo(articleComment.writerNickname)
            assertThat(findArticleComment.parentCommentWriterId).isEqualTo(articleComment.parentCommentWriterId)
            assertThat(findArticleComment.parentCommentWriterNickname).isEqualTo(articleComment.parentCommentWriterNickname)
            assertThat(findArticleComment.deleteStatus).isEqualTo(articleComment.deleteStatus)
            assertThat(findArticleComment.createdAt).isEqualTo(articleComment.createdAt)
            assertThat(findArticleComment.modifiedAt).isEqualTo(articleComment.modifiedAt)
        }


        @Test
        @DisplayName("조회 실패 시 null 반환")
        fun testNotFound() {
            // given
            // when
            val findArticleComment = articleCommentPersistencePortFixture.findById(14555665L)

            // then
            assertThat(findArticleComment).isNull()
        }
    }

    @Test
    @DisplayName("findAllPage: OffSet부터 시작하여, pageSize 만큼의 댓글을 가져온다.")
    fun findAllPageTest() {
        // given
        val articleId = 1234566L

        for (i in 1..10) {
            val commentId = i.toLong()
            val comment = if (commentId <= 5L) {
                articleCommentFixture(
                    articleCommentId = commentId,
                    rootParentCommentId = commentId,
                    articleId = articleId,
                )
            } else {
                articleCommentFixture(
                    articleCommentId = commentId,
                    rootParentCommentId = commentId - 5L,
                    articleId = articleId,
                )
            }
            articleCommentPersistencePortFixture.save(comment)
        }

        // when
        val articles = articleCommentPersistencePortFixture.findAllPage(
            articleId = articleId,
            offset = 3,
            limit = 3,
        )

        // then
        // 1
        // └ 6
        // 2
        // --------------------- 여기서부터
        // └ 7
        // 3
        // └ 8
        // ----------------------- 여기까지
        // 4
        // └ 9
        // 5
        // └ 10
        val articleCommentIds = articles.map { it.articleCommentId }

        assertThat(articleCommentIds.size).isEqualTo(3)
        assertThat(articleCommentIds).containsExactly(7, 3, 8)
    }

    @Nested
    @DisplayName("count: 최대 limit 건까지 범위 내에서 댓글의 갯수를 센다.")
    inner class CountTest {

        @Test
        @DisplayName("댓글 갯수가 limit 보다 같거나, 많으면, limit 만큼 갯수를 센다.")
        fun test1() {
            // given
            val articleId = 1234566L

            for (i in 1..10) {
                val commentId = i.toLong()
                val comment = if (commentId <= 5L) {
                    articleCommentFixture(
                        articleCommentId = commentId,
                        rootParentCommentId = commentId,
                        articleId = articleId,
                    )
                } else {
                    articleCommentFixture(
                        articleCommentId = commentId,
                        rootParentCommentId = commentId - 5L,
                        articleId = articleId,
                    )
                }
                articleCommentPersistencePortFixture.save(comment)
            }

            // when
            val count = articleCommentPersistencePortFixture.count(articleId, 9)

            // then
            assertThat(count).isEqualTo(9)
        }


        @Test
        @DisplayName("댓글 갯수가 limit 보다 적으면 댓글 갯수까지만큼 센다.")
        fun test2() {
            // given
            val articleId = 1234566L

            for (i in 1..10) {
                val commentId = i.toLong()
                val comment = if (commentId <= 5L) {
                    articleCommentFixture(
                        articleCommentId = commentId,
                        rootParentCommentId = commentId,
                        articleId = articleId,
                    )
                } else {
                    articleCommentFixture(
                        articleCommentId = commentId,
                        rootParentCommentId = commentId - 5L,
                        articleId = articleId,
                    )
                }
                articleCommentPersistencePortFixture.save(comment)
            }

            // when
            val count = articleCommentPersistencePortFixture.count(articleId, 15)

            // then
            assertThat(count).isEqualTo(10)
        }
    }

    @Nested
    @DisplayName("findAllInfiniteScroll : 댓글 무한스크롤 조회")
    inner class FindAllInfiniteScrollTest {


        @Test
        @DisplayName("lastCommentId, lastRootParentCommentId 가 null 이면 처음부터 limit 건 조회")
        fun test1() {
            // given
            val articleId = 1234566L
            for (i in 1..10) {
                val commentId = i.toLong()
                val comment = if (commentId <= 5L) {
                    articleCommentFixture(
                        articleCommentId = commentId,
                        rootParentCommentId = commentId,
                        articleId = articleId,
                    )
                } else {
                    articleCommentFixture(
                        articleCommentId = commentId,
                        rootParentCommentId = commentId - 5L,
                        articleId = articleId,
                    )
                }
                articleCommentPersistencePortFixture.save(comment)
            }


            // when
            val articles = articleCommentPersistencePortFixture.findAllInfiniteScroll(
                articleId = articleId,
                limit = 7,
                lastRootParentCommentId = null,
                lastCommentId = null
            )

            val articleCommentIds = articles.map { it.articleCommentId }

            assertThat(articleCommentIds.size).isEqualTo(7)
            assertThat(articleCommentIds).containsExactly(
                1, 6, 2, 7, 3, 8, 4
            )
        }

        @Test
        @DisplayName("lastCommentId, lastRootParentCommentId 가 모두 null 이 아니면, 해당 지점 지점 다음부터 조회")
        fun test2() {
            // given
            val articleId = 1234566L

            // 1.. 50 (루트 댓글)
            for (i in 1..50) {
                val articleCommentId = i.toLong()
                articleCommentPersistencePortFixture.save(
                    articleCommentFixture(
                        articleCommentId = articleCommentId,
                        articleId = articleId,
                        rootParentCommentId = articleCommentId
                    )
                )
            }

            // 51..150 (자식댓글)
            var subCommentId = 51L
            for (i in 1..50) {
                val rootParentCommentId = i.toLong()
                articleCommentPersistencePortFixture.save(
                    articleCommentFixture(
                        articleCommentId = subCommentId++,
                        articleId = articleId,
                        rootParentCommentId = rootParentCommentId
                    )
                )
                articleCommentPersistencePortFixture.save(
                    articleCommentFixture(
                        articleCommentId = subCommentId++,
                        articleId = articleId,
                        rootParentCommentId = rootParentCommentId
                    )
                )
            }

            //
            // 1
            // └ 51
            // └ 52
            // 2
            // └ 53
            // -----------------------------
            // └ 54
            // 3
            // └ 55
            // └ 56
            // 4
            // --------------------------

            // when
            val articles = articleCommentPersistencePortFixture.findAllInfiniteScroll(
                articleId = articleId,
                limit = 5,
                lastRootParentCommentId = 2,
                lastCommentId = 53
            )

            // then
            val articleCommentIds = articles.map { it.articleCommentId }
            assertThat(articleCommentIds.size).isEqualTo(5)
            assertThat(articleCommentIds).containsExactly(54, 3, 55, 56, 4)
        }
    }

}
