package com.ttasjwi.board.system.articleread.infra.persistence

import com.ttasjwi.board.system.articleread.infra.persistence.jpa.*
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.DataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-read-database-adapter] ArticleDetailStorageImpl 테스트")
class ArticleDetailStorageImplTest : DataBaseIntegrationTest() {

    @Nested
    @DisplayName("read: 게시글 상세 조회")
    inner class ReadTest {

        @Test
        @DisplayName("게시글을 찾지 못했을 경우, null 반환")
        fun articleNotFoundTest() {
            // given
            val articleId = 1237176453413L
            val userId = null

            // when
            val articleDetail = articleReadArticleDetailStorage.read(articleId, userId)

            // then
            assertThat(articleDetail).isNull()
        }


        @Test
        @DisplayName("게시글 조회 성공시, 게시글 기본 정보 및 게시판/게시글 카테고리 정보가 잘 조회되는 지 확인")
        fun testSuccessCommon() {
            // given
            val board = JpaBoard(
                boardId = 1341359813905137L,
                name = "고양이",
                description = "고양이 게시판입니다.",
                managerId = 11459014851L,
                slug = "cat",
                createdAt = appDateTimeFixture(minute = 23).toLocalDateTime()
            )
            entityManager.persist(board)

            val articleCategory = JpaArticleCategory(
                articleCategoryId = 45843951154L,
                boardId = board.boardId,
                name = "일반",
                slug = "general",
                allowWrite = true,
                allowSelfEditDelete = true,
                allowComment = true,
                allowLike = true,
                allowDislike = true,
                createdAt = appDateTimeFixture(minute = 27).toLocalDateTime()
            )
            entityManager.persist(articleCategory)

            val article = JpaArticle(
                articleId = 8384744815925L,
                title = "야옹야옹",
                content = "응애응애야오옹",
                boardId = board.boardId,
                articleCategoryId = articleCategory.articleCategoryId,
                writerId = 944495345048L,
                writerNickname = "땃쥐",
                createdAt = appDateTimeFixture(minute = 44).toLocalDateTime(),
                modifiedAt = appDateTimeFixture(minute = 49).toLocalDateTime()
            )
            entityManager.persist(article)

            val articleId = article.articleId
            val userId = null

            // when
            val articleDetail = articleReadArticleDetailStorage.read(articleId, userId)!!

            // then
            assertThat(articleDetail.articleId).isEqualTo(article.articleId)
            assertThat(articleDetail.title).isEqualTo(article.title)
            assertThat(articleDetail.content).isEqualTo(article.content)
            assertThat(articleDetail.articleCategory.articleCategoryId).isEqualTo(article.articleCategoryId)
            assertThat(articleDetail.articleCategory.name).isEqualTo(articleCategory.name)
            assertThat(articleDetail.articleCategory.slug).isEqualTo(articleCategory.slug)
            assertThat(articleDetail.articleCategory.allowSelfEditDelete).isEqualTo(articleCategory.allowSelfEditDelete)
            assertThat(articleDetail.articleCategory.allowLike).isEqualTo(articleCategory.allowLike)
            assertThat(articleDetail.articleCategory.allowDislike).isEqualTo(articleCategory.allowDislike)
            assertThat(articleDetail.board.boardId).isEqualTo(article.boardId)
            assertThat(articleDetail.board.name).isEqualTo(board.name)
            assertThat(articleDetail.board.slug).isEqualTo(board.slug)
            assertThat(articleDetail.writer.writerId).isEqualTo(article.writerId)
            assertThat(articleDetail.writer.nickname).isEqualTo(article.writerNickname)
            assertThat(articleDetail.createdAt.toLocalDateTime()).isEqualTo(article.createdAt)
            assertThat(articleDetail.modifiedAt.toLocalDateTime()).isEqualTo(article.modifiedAt)
        }

        @Test
        @DisplayName("좋아요수, 싫어요수, 댓글수가 저장되어 있을 경우, 값이 잘 조회되는 지 테스트")
        fun testLikeDislikeCommentCountNotNull() {
            // given
            val board = JpaBoard(
                boardId = 1341359813905137L,
                name = "고양이",
                description = "고양이 게시판입니다.",
                managerId = 11459014851L,
                slug = "cat",
                createdAt = appDateTimeFixture(minute = 23).toLocalDateTime()
            )
            entityManager.persist(board)

            val articleCategory = JpaArticleCategory(
                articleCategoryId = 45843951154L,
                boardId = board.boardId,
                name = "일반",
                slug = "general",
                allowWrite = true,
                allowSelfEditDelete = true,
                allowComment = true,
                allowLike = true,
                allowDislike = true,
                createdAt = appDateTimeFixture(minute = 27).toLocalDateTime()
            )
            entityManager.persist(articleCategory)

            val article = JpaArticle(
                articleId = 8384744815925L,
                title = "야옹야옹",
                content = "응애응애야오옹",
                boardId = board.boardId,
                articleCategoryId = articleCategory.articleCategoryId,
                writerId = 944495345048L,
                writerNickname = "땃쥐",
                createdAt = appDateTimeFixture(minute = 44).toLocalDateTime(),
                modifiedAt = appDateTimeFixture(minute = 49).toLocalDateTime()
            )
            entityManager.persist(article)


            val articleLikeCount = JpaArticleLikeCount(
                articleId = article.articleId,
                likeCount = 1341355L,
            )
            entityManager.persist(articleLikeCount)

            val articleDisLikeCount = JpaArticleDislikeCount(
                articleId = article.articleId,
                dislikeCount = 1455L,
            )
            entityManager.persist(articleDisLikeCount)

            val articleCommentCount = JpaArticleCommentCount(
                articleId = article.articleId,
                commentCount = 134134166L,
            )
            entityManager.persist(articleCommentCount)

            val articleId = article.articleId
            val userId = null

            // when
            val articleDetail = articleReadArticleDetailStorage.read(articleId, userId)!!

            // then
            assertThat(articleDetail.commentCount).isEqualTo(articleCommentCount.commentCount)
            assertThat(articleDetail.likeCount).isEqualTo(articleLikeCount.likeCount)
            assertThat(articleDetail.dislikeCount).isEqualTo(articleDisLikeCount.dislikeCount)
        }


        @Test
        @DisplayName("좋아요수, 싫어요수, 댓글수가 없을 경우, 값이 0으로 조회되는 지 테스트")
        fun testLikeDislikeCommentCountNull() {
            // given
            val board = JpaBoard(
                boardId = 1341359813905137L,
                name = "고양이",
                description = "고양이 게시판입니다.",
                managerId = 11459014851L,
                slug = "cat",
                createdAt = appDateTimeFixture(minute = 23).toLocalDateTime()
            )
            entityManager.persist(board)

            val articleCategory = JpaArticleCategory(
                articleCategoryId = 45843951154L,
                boardId = board.boardId,
                name = "일반",
                slug = "general",
                allowWrite = true,
                allowSelfEditDelete = true,
                allowComment = true,
                allowLike = true,
                allowDislike = true,
                createdAt = appDateTimeFixture(minute = 27).toLocalDateTime()
            )
            entityManager.persist(articleCategory)

            val article = JpaArticle(
                articleId = 8384744815925L,
                title = "야옹야옹",
                content = "응애응애야오옹",
                boardId = board.boardId,
                articleCategoryId = articleCategory.articleCategoryId,
                writerId = 944495345048L,
                writerNickname = "땃쥐",
                createdAt = appDateTimeFixture(minute = 44).toLocalDateTime(),
                modifiedAt = appDateTimeFixture(minute = 49).toLocalDateTime()
            )
            entityManager.persist(article)

            val articleId = article.articleId
            val userId = null

            // when
            val articleDetail = articleReadArticleDetailStorage.read(articleId, userId)!!

            // then
            assertThat(articleDetail.commentCount).isEqualTo(0L)
            assertThat(articleDetail.likeCount).isEqualTo(0L)
            assertThat(articleDetail.dislikeCount).isEqualTo(0L)
        }


        @Test
        @DisplayName("성공 - 회원아이디 값이 없을 경우, 좋아요 / 싫어요 여부는 false 로 반환된다.")
        fun whenUserIdIsNullThenLikedAndDislikedAreFalse() {
            // given
            val board = JpaBoard(
                boardId = 1341359813905137L,
                name = "고양이",
                description = "고양이 게시판입니다.",
                managerId = 11459014851L,
                slug = "cat",
                createdAt = appDateTimeFixture(minute = 23).toLocalDateTime()
            )
            entityManager.persist(board)

            val articleCategory = JpaArticleCategory(
                articleCategoryId = 45843951154L,
                boardId = board.boardId,
                name = "일반",
                slug = "general",
                allowWrite = true,
                allowSelfEditDelete = true,
                allowComment = true,
                allowLike = true,
                allowDislike = true,
                createdAt = appDateTimeFixture(minute = 27).toLocalDateTime()
            )
            entityManager.persist(articleCategory)

            val article = JpaArticle(
                articleId = 8384744815925L,
                title = "야옹야옹",
                content = "응애응애야오옹",
                boardId = board.boardId,
                articleCategoryId = articleCategory.articleCategoryId,
                writerId = 944495345048L,
                writerNickname = "땃쥐",
                createdAt = appDateTimeFixture(minute = 44).toLocalDateTime(),
                modifiedAt = appDateTimeFixture(minute = 49).toLocalDateTime()
            )
            entityManager.persist(article)

            val articleId = article.articleId
            val userId = null

            // when
            val articleDetail = articleReadArticleDetailStorage.read(articleId, userId)!!

            // then
            assertThat(articleDetail.liked).isFalse()
            assertThat(articleDetail.disliked).isFalse()
        }


        @Test
        @DisplayName("사용자의 좋아요, 싫어요 내역이 없을 경우 좋아요/싫어요 여부는 false 로 반환된다.")
        fun testLikedAndDislikedNull() {
            // given
            val board = JpaBoard(
                boardId = 1341359813905137L,
                name = "고양이",
                description = "고양이 게시판입니다.",
                managerId = 11459014851L,
                slug = "cat",
                createdAt = appDateTimeFixture(minute = 23).toLocalDateTime()
            )
            entityManager.persist(board)

            val articleCategory = JpaArticleCategory(
                articleCategoryId = 45843951154L,
                boardId = board.boardId,
                name = "일반",
                slug = "general",
                allowWrite = true,
                allowSelfEditDelete = true,
                allowComment = true,
                allowLike = true,
                allowDislike = true,
                createdAt = appDateTimeFixture(minute = 27).toLocalDateTime()
            )
            entityManager.persist(articleCategory)

            val article = JpaArticle(
                articleId = 8384744815925L,
                title = "야옹야옹",
                content = "응애응애야오옹",
                boardId = board.boardId,
                articleCategoryId = articleCategory.articleCategoryId,
                writerId = 944495345048L,
                writerNickname = "땃쥐",
                createdAt = appDateTimeFixture(minute = 44).toLocalDateTime(),
                modifiedAt = appDateTimeFixture(minute = 49).toLocalDateTime()
            )
            entityManager.persist(article)

            val articleId = article.articleId
            val userId = 8134151351513515L

            // when
            val articleDetail = articleReadArticleDetailStorage.read(articleId, userId)!!

            // then
            assertThat(articleDetail.liked).isFalse()
            assertThat(articleDetail.disliked).isFalse()
        }

        @Test
        @DisplayName("좋아요, 싫어요 내역이 있을 경우 좋아요/싫어요 여부는 true 로 반환된다.")
        fun testLikedAndDislikedNotNull() {
            // given
            val userId = 8134151351513515L


            val board = JpaBoard(
                boardId = 1341359813905137L,
                name = "고양이",
                description = "고양이 게시판입니다.",
                managerId = 11459014851L,
                slug = "cat",
                createdAt = appDateTimeFixture(minute = 23).toLocalDateTime()
            )
            entityManager.persist(board)

            val articleCategory = JpaArticleCategory(
                articleCategoryId = 45843951154L,
                boardId = board.boardId,
                name = "일반",
                slug = "general",
                allowWrite = true,
                allowSelfEditDelete = true,
                allowComment = true,
                allowLike = true,
                allowDislike = true,
                createdAt = appDateTimeFixture(minute = 27).toLocalDateTime()
            )
            entityManager.persist(articleCategory)

            val article = JpaArticle(
                articleId = 8384744815925L,
                title = "야옹야옹",
                content = "응애응애야오옹",
                boardId = board.boardId,
                articleCategoryId = articleCategory.articleCategoryId,
                writerId = 944495345048L,
                writerNickname = "땃쥐",
                createdAt = appDateTimeFixture(minute = 44).toLocalDateTime(),
                modifiedAt = appDateTimeFixture(minute = 49).toLocalDateTime()
            )
            entityManager.persist(article)

            val articleId = article.articleId

            val articleLike = JpaArticleLike(
                articleLikeId = 3585585785L,
                articleId = article.articleId,
                userId = userId,
                createdAt = appDateTimeFixture(minute = 51).toLocalDateTime()
            )
            entityManager.persist(articleLike)

            val articleDislike = JpaArticleDislike(
                articleDislikeId = 48585776767676L,
                articleId = article.articleId,
                userId = userId,
                createdAt = appDateTimeFixture(minute = 53).toLocalDateTime()
            )
            entityManager.persist(articleDislike)

            // when
            val articleDetail = articleReadArticleDetailStorage.read(articleId, userId)!!

            // then
            assertThat(articleDetail.liked).isTrue()
            assertThat(articleDetail.disliked).isTrue()
        }
    }
}
