package com.ttasjwi.board.system.articlelike.domain.port.fixture

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleCategoryFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-like-application-output-port] ArticleCategoryPersistencePortFixture 테스트")
class ArticleCategoryPersistencePortFixtureTest {

    private lateinit var articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture

    @BeforeEach
    fun setup() {
        articleCategoryPersistencePortFixture = ArticleCategoryPersistencePortFixture()
    }

    @Nested
    @DisplayName("findByIdOrNull : 조회 테스트")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("최초 생성 시 잘 저장되는 지 테스트")
        fun testCreate() {
            // given
            val articleCategory = articleCategoryFixture(
                articleCategoryId = 123123677L,
                allowLike = true,
                allowDislike = true,
            )

            // when
            articleCategoryPersistencePortFixture.save(articleCategory)
            val findArticleCategory =
                articleCategoryPersistencePortFixture.findByIdOrNull(articleCategory.articleCategoryId)!!

            // then
            assertThat(findArticleCategory.articleCategoryId).isEqualTo(articleCategory.articleCategoryId)
            assertThat(findArticleCategory.allowLike).isEqualTo(articleCategory.allowLike)
            assertThat(findArticleCategory.allowDislike).isEqualTo(articleCategory.allowDislike)
        }

        @Test
        @DisplayName("조회 결과물이 없으면 null 을 반환하는 지 테스트")
        fun testNotFound() {
            val findArticleCategory = articleCategoryPersistencePortFixture.findByIdOrNull(81233153L)

            assertThat(findArticleCategory).isNull()
        }
    }
}
