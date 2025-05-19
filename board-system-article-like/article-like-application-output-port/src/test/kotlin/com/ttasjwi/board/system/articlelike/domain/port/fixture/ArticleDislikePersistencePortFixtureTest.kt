package com.ttasjwi.board.system.articlelike.domain.port.fixture

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleDislikeFixture
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeFixture
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-like-application-output-port] ArticleDislikePersistencePortFixture 테스트")
class ArticleDislikePersistencePortFixtureTest {

    private lateinit var articleDislikePersistencePortFixture: ArticleDislikePersistencePortFixture

    @BeforeEach
    fun setup() {
        articleDislikePersistencePortFixture = ArticleDislikePersistencePortFixture()
    }

    @Nested
    @DisplayName("findByIdOrNull: 조회 테스트")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("성공 테스트")
        fun test1() {
            // given
            val articleDislike = articleDislikeFixture(
                articleDislikeId = 123L,
                articleId = 15L,
                userId = 13456L,
            )
            articleDislikePersistencePortFixture.save(articleDislike)

            // when
            val findArticleDislike = articleDislikePersistencePortFixture.findByIdOrNullTest(articleDislike.articleDislikeId)!!

            // then
            assertThat(findArticleDislike.articleDislikeId).isEqualTo(articleDislike.articleDislikeId)
            assertThat(findArticleDislike.articleId).isEqualTo(findArticleDislike.articleId)
            assertThat(findArticleDislike.userId).isEqualTo(findArticleDislike.userId)
            assertThat(findArticleDislike.createdAt).isEqualTo(findArticleDislike.createdAt)
        }

        @Test
        @DisplayName("조회 실패시 null 반환")
        fun test2() {
            // given
            // when
            val findArticleDislike = articleDislikePersistencePortFixture.findByIdOrNullTest(283837475L)

            // then
            assertThat(findArticleDislike).isNull()
        }
    }

    @Nested
    @DisplayName("findByArticleIdAndUserIdOrNull: 게시글아이디/사용자아이디로 조회 테스트")
    inner class FindByArticleIdAndUserIdOrNullTest {

        @Test
        @DisplayName("성공 테스트")
        fun test1() {
            // given
            val articleDislike = articleDislikeFixture(
                articleDislikeId = 123L,
                articleId = 15L,
                userId = 13456L,
            )
            articleDislikePersistencePortFixture.save(articleDislike)

            // when
            val findArticleDislike = articleDislikePersistencePortFixture.findByArticleIdAndUserIdOrNull(articleDislike.articleId, articleDislike.userId)!!

            // then
            assertThat(findArticleDislike.articleDislikeId).isEqualTo(articleDislike.articleDislikeId)
            assertThat(findArticleDislike.articleId).isEqualTo(findArticleDislike.articleId)
            assertThat(findArticleDislike.userId).isEqualTo(findArticleDislike.userId)
            assertThat(findArticleDislike.createdAt).isEqualTo(findArticleDislike.createdAt)
        }

        @Test
        @DisplayName("조회 실패시 null 반환")
        fun test2() {
            // given
            // when
            val findArticleDislike = articleDislikePersistencePortFixture.findByArticleIdAndUserIdOrNull(
                articleId = 245L,
                userId = 13456L,
            )

            // then
            assertThat(findArticleDislike).isNull()
        }
    }

    @Nested
    @DisplayName("existsByArticleIdAndUserId: 게시글아이디/사용자아이디로 존재여부 확인 테스트")
    inner class ExistsByArticleIdAndUserIdTest {

        @Test
        @DisplayName("존재할 경우 true 반환")
        fun test1() {
            // given
            val articleDislike = articleDislikeFixture(
                articleDislikeId = 123L,
                articleId = 15L,
                userId = 13456L,
            )
            articleDislikePersistencePortFixture.save(articleDislike)

            // when
            val exists = articleDislikePersistencePortFixture.existsByArticleIdAndUserId(articleDislike.articleId, articleDislike.userId)

            // then
            assertThat(exists).isTrue()
        }

        @Test
        @DisplayName("존재하지 않을 경우 false 반환")
        fun test2() {
            // given
            // when
            val exists = articleDislikePersistencePortFixture.existsByArticleIdAndUserId(
                articleId = 245L,
                userId = 13456L,
            )

            // then
            assertThat(exists).isFalse()
        }
    }
}
