package com.ttasjwi.board.system.articlelike.infra.persistence

import com.ttasjwi.board.system.articlelike.infra.persistence.jpa.JpaArticleCategory
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.DataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-like-database-adapter] ArticleCategoryPersistenceAdapter 테스트")
class ArticleCategoryPersistenceAdapterTest : DataBaseIntegrationTest() {

    @Nested
    @DisplayName("findByIdOrNull : 조회 테스트")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("최초 생성 시 잘 저장되는 지 테스트")
        fun testCreate() {
            // given
            val articleCategory = JpaArticleCategory(
                articleCategoryId = 15L,
                boardId = 13L,
                name = "일반",
                slug = "general",
                allowSelfDelete = true,
                allowLike = true,
                allowDislike = true,
                createdAt = appDateTimeFixture().toLocalDateTime()
            )

            // when
            entityManager.persist(articleCategory)
            flushAndClearEntityManager()

            val findArticleCategory =
                articleLikeArticleCategoryPersistenceAdapter.findByIdOrNull(articleCategory.articleCategoryId)!!

            // then
            assertThat(findArticleCategory.articleCategoryId).isEqualTo(articleCategory.articleCategoryId)
            assertThat(findArticleCategory.allowLike).isEqualTo(articleCategory.allowLike)
            assertThat(findArticleCategory.allowDislike).isEqualTo(articleCategory.allowDislike)
        }

        @Test
        @DisplayName("조회 결과물이 없으면 null 을 반환하는 지 테스트")
        fun testNotFound() {
            val findArticleCategory = articleLikeArticleCategoryPersistenceAdapter.findByIdOrNull(81233153L)
            assertThat(findArticleCategory).isNull()
        }
    }
}
