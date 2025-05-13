package com.ttasjwi.board.system.articlecomment.infra.persistence

import com.ttasjwi.board.system.articlecomment.infra.persistence.jpa.JpaArticle
import com.ttasjwi.board.system.articlecomment.infra.test.ArticleCommentDataBaseIntegrationTest
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-database-adapter] ArticlePersistenceAdapter 테스트")
class ArticlePersistenceAdapterTest : ArticleCommentDataBaseIntegrationTest() {

    @Nested
    @DisplayName("저장 후 식별자 조회 테스트")
    inner class SaveAndFindTest {

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

            // when
            entityManager.persist(article)
            flushAndClearEntityManager()
            val findArticle = articlePersistenceAdapter.findById(article.articleId)!!

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
            val findArticle = articlePersistenceAdapter.findById(14555665L)

            // then
            assertThat(findArticle).isNull()
        }
    }
}
