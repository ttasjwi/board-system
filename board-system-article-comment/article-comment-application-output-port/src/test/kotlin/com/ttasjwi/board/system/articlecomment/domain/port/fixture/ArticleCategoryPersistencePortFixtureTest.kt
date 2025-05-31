package com.ttasjwi.board.system.articlecomment.domain.port.fixture

import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleCategoryFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ArticleCategoryPersistencePortFixtureTest {

    private lateinit var articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture

    @BeforeEach
    fun setup() {
        articleCategoryPersistencePortFixture = ArticleCategoryPersistencePortFixture()
    }

    @Nested
    @DisplayName("findByIdOrNull : 저장 후 조회 테스트")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("저장된 것이 잘 조회되는 지 테스트")
        fun test1() {
            // given
            val articleCategory = articleCategoryFixture(
                articleCategoryId = 15L,
                allowComment = true,
            )

            articleCategoryPersistencePortFixture.save(articleCategory)

            // when
            val findArticleCategory =
                articleCategoryPersistencePortFixture.findByIdOrNull(articleCategory.articleCategoryId)!!

            // then
            assertThat(findArticleCategory.articleCategoryId).isEqualTo(articleCategory.articleCategoryId)
            assertThat(findArticleCategory.allowComment).isEqualTo(articleCategory.allowComment)
        }

        @Test
        @DisplayName("조회 결과물이 없으면 null 을 반환하는 지 테스트")
        fun test2() {
            val findArticleCategory =
                articleCategoryPersistencePortFixture.findByIdOrNull(articleCategoryId = 314151235L)
            assertThat(findArticleCategory).isNull()
        }
    }
}
