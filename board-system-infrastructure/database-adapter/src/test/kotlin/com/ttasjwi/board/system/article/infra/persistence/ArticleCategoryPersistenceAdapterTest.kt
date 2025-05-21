package com.ttasjwi.board.system.article.infra.persistence

import com.ttasjwi.board.system.article.infra.persistence.jpa.JpaArticleCategory
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.DataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-database-adapter] ArticleCategoryPersistenceAdapter 테스트")
class ArticleCategoryPersistenceAdapterTest : DataBaseIntegrationTest() {

    @Nested
    @DisplayName("find : 저장 후 조회 테스트")
    inner class FindTest {

        @Test
        @DisplayName("저장된 것이 잘 조회되는 지 테스트")
        fun testCreate() {
            // given
            val articleCategory = JpaArticleCategory(
                articleCategoryId = 1234566L,
                boardId = 123456L,
                name = "질문",
                slug = "question",
                allowSelfDelete = false,
                allowLike = true,
                allowDislike = true,
                createdAt = appDateTimeFixture(minute = 13).toLocalDateTime()
            )

            // when
            entityManager.persist(articleCategory)
            flushAndClearEntityManager()
            val findArticleCategory =
                articleArticleCategoryPersistenceAdapter.findByIdOrNull(articleCategory.articleCategoryId)!!

            // then
            assertThat(findArticleCategory.articleCategoryId).isEqualTo(articleCategory.articleCategoryId)
            assertThat(findArticleCategory.boardId).isEqualTo(articleCategory.boardId)
            assertThat(findArticleCategory.allowSelfDelete).isEqualTo(articleCategory.allowSelfDelete)
        }

        @Test
        @DisplayName("조회 결과물이 없으면 null 을 반환하는 지 테스트")
        fun testNotFound() {
            val findArticleCategory = articleArticleCategoryPersistenceAdapter.findByIdOrNull(81233153L)
            assertThat(findArticleCategory).isNull()
        }
    }
}
