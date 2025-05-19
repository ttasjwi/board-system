package com.ttasjwi.board.system.articlelike.domain.port.fixture

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-like-application-output-port] ArticlePersistencePortFixture 테스트")
class ArticlePersistencePortFixtureTest {

    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setup() {
        articlePersistencePortFixture = ArticlePersistencePortFixture()
    }

    @Nested
    @DisplayName("findByIdOrNull : articleId 값으로 게시글 조회")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("성공 테스트")
        fun test1() {
            // given
            val article = articleFixture(
                articleId = 123L,
                articleCategoryId = 13445L
            )
            articlePersistencePortFixture.save(article)

            // when
            val findArticle = articlePersistencePortFixture.findByIdOrNull(article.articleId)!!

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
            val findArticleLikeCount = articlePersistencePortFixture.findByIdOrNull(articleId)

            assertThat(findArticleLikeCount).isNull()
        }
    }


    @Nested
    @DisplayName("existsById : articleId 값으로 게시글 존재여부 확인")
    inner class ExistsByIdTest {

        @Test
        @DisplayName("존재하면 true 반환")
        fun test1() {
            // given
            val article = articleFixture(
                articleId = 123L,
                articleCategoryId = 13445L
            )
            articlePersistencePortFixture.save(article)

            // when
            val exists = articlePersistencePortFixture.existsByArticleId(article.articleId)

            // then
            assertThat(exists).isTrue()
        }


        @Test
        @DisplayName("존재하지 않으면 false 반환")
        fun test2() {
            // given
            val articleId = 15555L

            // when
            // then
            val exists = articlePersistencePortFixture.existsByArticleId(articleId)

            assertThat(exists).isFalse()
        }
    }
}
