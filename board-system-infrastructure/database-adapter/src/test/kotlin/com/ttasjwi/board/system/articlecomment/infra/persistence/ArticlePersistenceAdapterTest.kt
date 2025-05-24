package com.ttasjwi.board.system.articlecomment.infra.persistence

import com.ttasjwi.board.system.articlecomment.infra.persistence.jpa.JpaArticle
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.DataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-database-adapter] ArticlePersistenceAdapter 테스트")
class ArticlePersistenceAdapterTest : DataBaseIntegrationTest() {

    @Nested
    @DisplayName("findById: 게시글을 조회하고, 존재하지 않으면 null 반환")
    inner class FindByIdTest {


        @Test
        @DisplayName("조회 성공하는 경우")
        fun testSuccess() {
            // given
            val article = JpaArticle(
                articleId = 12314567L,
                writerId = 12334L,
                articleCategoryId = 212431341L,
                title = "제목",
                content = "본문",
                boardId = 1231141234L,
                writerNickname = "땃고양이",
                createdAt = appDateTimeFixture(minute = 14).toLocalDateTime(),
                modifiedAt = appDateTimeFixture(minute = 14).toLocalDateTime(),
            )
            entityManager.persist(article)
            flushAndClearEntityManager()

            // when
            val findArticle = articleCommentArticlePersistenceAdapter.findById(article.articleId)!!

            // then
            assertThat(findArticle.articleId).isEqualTo(article.articleId)
            assertThat(findArticle.writerId).isEqualTo(article.writerId)
            assertThat(findArticle.articleCategoryId).isEqualTo(article.articleCategoryId)
        }


        @Test
        @DisplayName("조회 실패 시 null 반환")
        fun testNotFound() {
            // given
            // when
            val findArticle = articleCommentArticlePersistenceAdapter.findById(14555665L)

            // then
            assertThat(findArticle).isNull()
        }
    }

    @Nested
    @DisplayName("existsById: 게시글 존재 여부를 반환한다.")
    inner class ExistsByIdTest {
        @Test
        @DisplayName("게시글이 존재하면 true 반환")
        fun testTrue() {
            // given
            val article = JpaArticle(
                articleId = 12314567L,
                writerId = 12334L,
                articleCategoryId = 212431341L,
                title = "제목",
                content = "본문",
                boardId = 1231141234L,
                writerNickname = "땃고양이",
                createdAt = appDateTimeFixture(minute = 14).toLocalDateTime(),
                modifiedAt = appDateTimeFixture(minute = 14).toLocalDateTime(),
            )

            // when
            entityManager.persist(article)
            flushAndClearEntityManager()

            // when
            val exists = articleCommentArticlePersistenceAdapter.existsById(article.articleId)

            // then
            assertThat(exists).isTrue
        }


        @Test
        @DisplayName("게시글이 존재하지 않으면 false 반환")
        fun testFalse() {
            // given
            // when
            val exists = articleCommentArticlePersistenceAdapter.existsById(14555665L)

            // then
            assertThat(exists).isFalse
        }
    }
}
