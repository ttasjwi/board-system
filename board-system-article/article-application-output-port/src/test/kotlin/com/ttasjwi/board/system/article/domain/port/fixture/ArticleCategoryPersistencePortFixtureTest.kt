package com.ttasjwi.board.system.article.domain.port.fixture

import com.ttasjwi.board.system.article.domain.model.fixture.articleCategoryFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-application-output-port] ArticleCategoryPersistencePortFixture 테스트")
class ArticleCategoryPersistencePortFixtureTest {

    private lateinit var articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture

    @BeforeEach
    fun setup() {
        articleCategoryPersistencePortFixture = ArticleCategoryPersistencePortFixture()
    }

    @Nested
    @DisplayName("find : 저장 후 조회 테스트")
    inner class FindTest {

        @Test
        @DisplayName("저장된 것이 잘 조회되는 지 테스트")
        fun testCreate() {
            // given
            val articleCategory = articleCategoryFixture(
                articleCategoryId = 1234566L,
                boardId = 123456L,
                allowWrite = true,
                allowSelfEditDelete = false,
            )

            // when
            articleCategoryPersistencePortFixture.save(articleCategory)
            val findArticleCategory = articleCategoryPersistencePortFixture.findByIdOrNull(articleCategory.articleCategoryId)!!

            // then
            assertThat(findArticleCategory.articleCategoryId).isEqualTo(articleCategory.articleCategoryId)
            assertThat(findArticleCategory.boardId).isEqualTo(articleCategory.boardId)
            assertThat(findArticleCategory.allowWrite).isEqualTo(findArticleCategory.allowWrite)
            assertThat(findArticleCategory.allowSelfEditDelete).isEqualTo(articleCategory.allowSelfEditDelete)
        }

        @Test
        @DisplayName("조회 결과물이 없으면 null 을 반환하는 지 테스트")
        fun testNotFound() {
            val findArticleCategory = articleCategoryPersistencePortFixture.findByIdOrNull(81233153L)
            assertThat(findArticleCategory).isNull()
        }
    }
}
