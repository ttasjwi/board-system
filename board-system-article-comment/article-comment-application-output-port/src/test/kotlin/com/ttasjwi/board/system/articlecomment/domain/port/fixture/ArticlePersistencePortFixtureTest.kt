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
    @DisplayName("findById: 게시글을 조회하고, 존재하지 않으면 null 반환")
    inner class FindByIdTest {


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

    @Nested
    @DisplayName("existsById: 게시글 존재 여부를 반환한다.")
    inner class ExistsByIdTest {
        @Test
        @DisplayName("게시글이 존재하면 true 반환")
        fun testTrue() {
            // given
            val article = articleFixture()
            articlePersistencePortFixture.save(article)

            // when
            val exists = articlePersistencePortFixture.existsById(article.articleId)

            // then
            assertThat(exists).isTrue
        }


        @Test
        @DisplayName("게시글이 존재하지 않으면 false 반환")
        fun testFalse() {
            // given
            // when
            val exists = articlePersistencePortFixture.existsById(14555665L)

            // then
            assertThat(exists).isFalse
        }
    }
}
