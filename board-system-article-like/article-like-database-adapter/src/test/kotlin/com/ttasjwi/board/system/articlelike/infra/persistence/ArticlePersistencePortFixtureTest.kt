package com.ttasjwi.board.system.articlelike.infra.persistence

import com.ttasjwi.board.system.articlelike.infra.persistence.jpa.JpaArticle
import com.ttasjwi.board.system.articlelike.infra.test.ArticleLikeDataBaseIntegrationTest
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-like-database-adapter] ArticlePersistenceAdapter 테스트")
class ArticlePersistencePortFixtureTest : ArticleLikeDataBaseIntegrationTest() {

    @Nested
    @DisplayName("findByIdOrNull : articleId 값으로 게시글 조회")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("성공 테스트")
        fun test1() {
            // given
            val article = JpaArticle(
                articleId = 123L,
                articleCategoryId = 13445L,
                title = "제목",
                content = "본문",
                boardId = 1234L,
                writerId = 122L,
                writerNickname = "땃쥐",
                createdAt = appDateTimeFixture().toLocalDateTime(),
                modifiedAt = appDateTimeFixture().toLocalDateTime(),
            )
            entityManager.persist(article)
            flushAndClearEntityManager()

            // when
            val findArticle = articleLikeArticlePersistenceAdapter.findByIdOrNull(article.articleId)!!

            // then
            assertThat(findArticle.articleId).isEqualTo(article.articleId)
            assertThat(findArticle.articleCategoryId).isEqualTo(article.articleCategoryId)
        }


        @Test
        @DisplayName("조회 실패시 null 반환 테스트")
        fun test2() {
            // given
            val articleId = 15555L

            // when
            // then
            val findArticleLikeCount = articleLikeArticlePersistenceAdapter.findByIdOrNull(articleId)

            assertThat(findArticleLikeCount).isNull()
        }
    }
}
