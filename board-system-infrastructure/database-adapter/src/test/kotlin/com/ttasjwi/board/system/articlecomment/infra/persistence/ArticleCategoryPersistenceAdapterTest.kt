package com.ttasjwi.board.system.articlecomment.infra.persistence

import com.ttasjwi.board.system.articlecomment.infra.persistence.jpa.JpaArticleCategory
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.DataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-database-adapter] ArticleCategoryPersistenceAdapter 테스트")
class ArticleCategoryPersistenceAdapterTest : DataBaseIntegrationTest() {


    @Nested
    @DisplayName("findByIdOrNull : 저장 후 조회 테스트")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("저장된 것이 잘 조회되는 지 테스트")
        fun test1() {
            // given
            val articleCategory = JpaArticleCategory(
                articleCategoryId = 15L,
                boardId = 13L,
                name = "일반",
                slug = "general",
                allowWrite = true,
                allowSelfEditDelete = true,
                allowComment = true,
                allowLike = true,
                allowDislike = true,
                createdAt = appDateTimeFixture().toLocalDateTime()
            )

            entityManager.persist(articleCategory)
            flushAndClearEntityManager()

            // when
            val findArticleCategory = articleCommentArticleCategoryPersistenceAdapter.findByIdOrNull(articleCategory.articleCategoryId)!!

            // then
            assertThat(findArticleCategory.articleCategoryId).isEqualTo(articleCategory.articleCategoryId)
            assertThat(findArticleCategory.allowComment).isEqualTo(articleCategory.allowComment)
        }

        @Test
        @DisplayName("조회 결과물이 없으면 null 을 반환하는 지 테스트")
        fun test2() {
            val findArticleCategory = articleCommentArticleCategoryPersistenceAdapter.findByIdOrNull(articleCategoryId = 314151235L)
            assertThat(findArticleCategory).isNull()
        }
    }
}
