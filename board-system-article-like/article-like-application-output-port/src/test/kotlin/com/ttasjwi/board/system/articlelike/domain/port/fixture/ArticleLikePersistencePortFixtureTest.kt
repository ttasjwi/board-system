package com.ttasjwi.board.system.articlelike.domain.port.fixture

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeFixture
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-like-application-output-port] ArticleLikePersistencePortFixture 테스트")
class ArticleLikePersistencePortFixtureTest {

    private lateinit var articleLikePersistencePortFixture: ArticleLikePersistencePortFixture

    @BeforeEach
    fun setup() {
        articleLikePersistencePortFixture = ArticleLikePersistencePortFixture()
    }

    @Nested
    @DisplayName("findByIdOrNull: 조회 테스트")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("성공 테스트")
        fun test1() {
            // given
            val articleLike = articleLikeFixture(
                articleLikeId = 123L,
                articleId = 15L,
                userId = 13456L,
            )
            articleLikePersistencePortFixture.save(articleLike)

            // when
            val findArticleLike = articleLikePersistencePortFixture.findByIdOrNullTest(articleLike.articleLikeId)!!

            // then
            assertThat(findArticleLike.articleLikeId).isEqualTo(articleLike.articleLikeId)
            assertThat(findArticleLike.articleId).isEqualTo(findArticleLike.articleId)
            assertThat(findArticleLike.userId).isEqualTo(findArticleLike.userId)
            assertThat(findArticleLike.createdAt).isEqualTo(findArticleLike.createdAt)
        }

        @Test
        @DisplayName("조회 실패시 null 반환")
        fun test2() {
            // given
            // when
            val findArticleLike = articleLikePersistencePortFixture.findByIdOrNullTest(283837475L)

            // then
            assertThat(findArticleLike).isNull()
        }
    }

    @Nested
    @DisplayName("findByArticleIdAndUserIdOrNull: 게시글아이디/사용자아이디로 조회 테스트")
    inner class FindByArticleIdAndUserIdOrNullTest {

        @Test
        @DisplayName("성공 테스트")
        fun test1() {
            // given
            val articleLike = articleLikeFixture(
                articleLikeId = 123L,
                articleId = 15L,
                userId = 13456L,
            )
            articleLikePersistencePortFixture.save(articleLike)

            // when
            val findArticleLike = articleLikePersistencePortFixture.findByArticleIdAndUserIdOrNull(articleLike.articleId, articleLike.userId)!!

            // then
            assertThat(findArticleLike.articleLikeId).isEqualTo(articleLike.articleLikeId)
            assertThat(findArticleLike.articleId).isEqualTo(findArticleLike.articleId)
            assertThat(findArticleLike.userId).isEqualTo(findArticleLike.userId)
            assertThat(findArticleLike.createdAt).isEqualTo(findArticleLike.createdAt)
        }

        @Test
        @DisplayName("조회 실패시 null 반환")
        fun test2() {
            // given
            // when
            val findArticleLike = articleLikePersistencePortFixture.findByArticleIdAndUserIdOrNull(
                articleId = 245L,
                userId = 13456L,
            )

            // then
            assertThat(findArticleLike).isNull()
        }
    }

    @Nested
    @DisplayName("existsByArticleIdAndUserId: 게시글아이디/사용자아이디로 존재여부 확인 테스트")
    inner class ExistsByArticleIdAndUserIdTest {

        @Test
        @DisplayName("존재할 경우 true 반환")
        fun test1() {
            // given
            val articleLike = articleLikeFixture(
                articleLikeId = 123L,
                articleId = 15L,
                userId = 13456L,
            )
            articleLikePersistencePortFixture.save(articleLike)

            // when
            val exists = articleLikePersistencePortFixture.existsByArticleIdAndUserId(articleLike.articleId, articleLike.userId)

            // then
            assertThat(exists).isTrue()
        }

        @Test
        @DisplayName("존재하지 않을 경우 false 반환")
        fun test2() {
            // given
            // when
            val exists = articleLikePersistencePortFixture.existsByArticleIdAndUserId(
                articleId = 245L,
                userId = 13456L,
            )

            // then
            assertThat(exists).isFalse()
        }
    }
}
