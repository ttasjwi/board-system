package com.ttasjwi.board.system.articlecomment.domain.port.fixture

import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-application-output-port] ArticlePersistencePortFixture 테스트")
class ArticlePersistencePortFixtureTest {

    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setUp() {
        articlePersistencePortFixture = ArticlePersistencePortFixture()
    }

    @Nested
    @DisplayName("저장 후 식별자 조회 테스트")
    inner class SaveAndFindTest {


        @Test
        @DisplayName("조회 성공하는 경우")
        fun testSuccess() {
            // given
            val article = articleFixture()

            // when
            articlePersistencePortFixture.save(article)
            val findArticle = articlePersistencePortFixture.findById(article.articleId)!!

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
            val findArticle = articlePersistencePortFixture.findById(14555665L)

            // then
            assertThat(findArticle).isNull()
        }
    }
}
