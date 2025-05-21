package com.ttasjwi.board.system.articlelike.infra.persistence

import com.ttasjwi.board.system.articlelike.infra.test.ArticleLikeDataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-like-database-adapter] ArticleLikeCountPersistenceAdapter 테스트")
class ArticleLikeCountPersistenceAdapterTest : ArticleLikeDataBaseIntegrationTest() {

    @Nested
    @DisplayName("increase: 좋아요 수 증가")
    inner class IncreaseTest {

        @Test
        @DisplayName("최초 좋아요 테스트")
        fun test1() {
            // given
            val articleId = 5857L

            // when
            articleLikeArticleLikeCountPersistenceAdapter.increase(articleId)


            // then
            val articleLikeCount = articleLikeArticleLikeCountPersistenceAdapter.findByIdOrNull(articleId)!!

            assertThat(articleLikeCount.articleId).isEqualTo(articleId)
            assertThat(articleLikeCount.likeCount).isEqualTo(1)
        }

        @Test
        @DisplayName("첫번째 이후 좋아요 테스트")
        fun test2() {
            // given
            val articleId = 5857L

            // when
            articleLikeArticleLikeCountPersistenceAdapter.increase(articleId)
            articleLikeArticleLikeCountPersistenceAdapter.increase(articleId)

            // then
            val articleLikeCount = articleLikeArticleLikeCountPersistenceAdapter.findByIdOrNull(articleId)!!

            assertThat(articleLikeCount.articleId).isEqualTo(articleId)
            assertThat(articleLikeCount.likeCount).isEqualTo(2)
        }
    }


    @Nested
    @DisplayName("decrease: 좋아요 수 감소")
    inner class DecreaseTest {

        @Test
        @DisplayName("두번 좋아요 수 증가 후, 좋아요수 감소 테스트")
        fun test() {
            // given
            val articleId = 5857L

            // when
            articleLikeArticleLikeCountPersistenceAdapter.increase(articleId)
            articleLikeArticleLikeCountPersistenceAdapter.increase(articleId)
            articleLikeArticleLikeCountPersistenceAdapter.decrease(articleId)

            // then
            val articleLikeCount = articleLikeArticleLikeCountPersistenceAdapter.findByIdOrNull(articleId)!!

            assertThat(articleLikeCount.articleId).isEqualTo(articleId)
            assertThat(articleLikeCount.likeCount).isEqualTo(1)
        }

    }


    @Nested
    @DisplayName("findByIdOrNull : articleId 값으로 좋아요 수 조회")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("조회 실패시 null 반환 테스트")
        fun test2() {
            // given
            val articleId = 15555L

            // when
            // then
            val findArticleLikeCount = articleLikeArticleLikeCountPersistenceAdapter.findByIdOrNull(articleId)

            assertThat(findArticleLikeCount).isNull()
        }
    }
}
