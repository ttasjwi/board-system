package com.ttasjwi.board.system.articlelike.domain.port.fixture

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeCountFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-like-application-output-port] ArticleLikeCountPersistencePortFixture 테스트")
class ArticleLikeCountPersistencePortFixtureTest {

    private lateinit var articleLikeCountPersistencePortFixture: ArticleLikeCountPersistencePortFixture

    @BeforeEach
    fun setup() {
        articleLikeCountPersistencePortFixture = ArticleLikeCountPersistencePortFixture()
    }

    @Nested
    @DisplayName("increase: 좋아요 수 증가")
    inner class IncreaseTest {

        @Test
        @DisplayName("최초 좋아요 테스트")
        fun test1() {
            // given
            val articleId = 5857L

            // when
            articleLikeCountPersistencePortFixture.increase(articleId)


            // then
            val articleLikeCount = articleLikeCountPersistencePortFixture.findByIdOrNull(articleId)!!

            assertThat(articleLikeCount.articleId).isEqualTo(articleId)
            assertThat(articleLikeCount.likeCount).isEqualTo(1)
        }

        @Test
        @DisplayName("첫번째 이후 좋아요 테스트")
        fun test2() {
            // given
            val articleId = 5857L

            // when
            articleLikeCountPersistencePortFixture.increase(articleId)
            articleLikeCountPersistencePortFixture.increase(articleId)


            // then
            val articleLikeCount = articleLikeCountPersistencePortFixture.findByIdOrNull(articleId)!!

            assertThat(articleLikeCount.articleId).isEqualTo(articleId)
            assertThat(articleLikeCount.likeCount).isEqualTo(2)
        }

    }

    @Nested
    @DisplayName("findByIdOrNull : articleId 값으로 좋아요 수 조회")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("성공 테스트")
        fun test1() {
            // given
            val articleId = 13L
            val articleLikeCount = articleLikeCountFixture(
                articleId = articleId,
                likeCount = 1345L
            )
            articleLikeCountPersistencePortFixture.save(articleLikeCount)

            // when
            val findArticleLikeCount = articleLikeCountPersistencePortFixture.findByIdOrNull(articleId)!!

            // then
            assertThat(findArticleLikeCount.articleId).isEqualTo(articleLikeCount.articleId)
            assertThat(findArticleLikeCount.likeCount).isEqualTo(articleLikeCount.likeCount)
        }


        @Test
        @DisplayName("조회 실패시 null 반환 테스트")
        fun test2() {
            // given
            val articleId = 15555L

            // when
            // then
            val findArticleLikeCount = articleLikeCountPersistencePortFixture.findByIdOrNull(articleId)

            assertThat(findArticleLikeCount).isNull()
        }
    }
}
