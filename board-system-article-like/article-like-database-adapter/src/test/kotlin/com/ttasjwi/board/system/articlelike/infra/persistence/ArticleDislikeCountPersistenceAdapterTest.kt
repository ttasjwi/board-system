package com.ttasjwi.board.system.articlelike.infra.persistence

import com.ttasjwi.board.system.articlelike.infra.test.ArticleLikeDataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-like-database-adapter] ArticleDislikeCountPersistenceAdapter 테스트")
class ArticleDislikeCountPersistenceAdapterTest : ArticleLikeDataBaseIntegrationTest() {

    @Nested
    @DisplayName("increase: 싫어요 수 증가")
    inner class IncreaseTest {

        @Test
        @DisplayName("최초 싫어요 테스트")
        fun test1() {
            // given
            val articleId = 5857L

            // when
            articleLikeArticleDislikeCountPersistenceAdapter.increase(articleId)


            // then
            val articleDislikeCount = articleLikeArticleDislikeCountPersistenceAdapter.findByIdOrNull(articleId)!!

            assertThat(articleDislikeCount.articleId).isEqualTo(articleId)
            assertThat(articleDislikeCount.dislikeCount).isEqualTo(1)
        }

        @Test
        @DisplayName("첫번째 이후 싫어요 테스트")
        fun test2() {
            // given
            val articleId = 5857L

            // when
            articleLikeArticleDislikeCountPersistenceAdapter.increase(articleId)
            articleLikeArticleDislikeCountPersistenceAdapter.increase(articleId)

            // then
            val articleDislikeCount = articleLikeArticleDislikeCountPersistenceAdapter.findByIdOrNull(articleId)!!

            assertThat(articleDislikeCount.articleId).isEqualTo(articleId)
            assertThat(articleDislikeCount.dislikeCount).isEqualTo(2)
        }
    }


    @Nested
    @DisplayName("decrease: 싫어요 수 감소")
    inner class DecreaseTest {

        @Test
        @DisplayName("두번 싫어요 수 증가 후, 싫어요수 감소 테스트")
        fun test() {
            // given
            val articleId = 5857L

            // when
            articleLikeArticleDislikeCountPersistenceAdapter.increase(articleId)
            articleLikeArticleDislikeCountPersistenceAdapter.increase(articleId)
            articleLikeArticleDislikeCountPersistenceAdapter.decrease(articleId)

            // then
            val articleDislikeCount = articleLikeArticleDislikeCountPersistenceAdapter.findByIdOrNull(articleId)!!

            assertThat(articleDislikeCount.articleId).isEqualTo(articleId)
            assertThat(articleDislikeCount.dislikeCount).isEqualTo(1)
        }

    }

    @Nested
    @DisplayName("findByIdOrNull : articleId 값으로 싫어요 수 조회")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("조회 실패시 null 반환 테스트")
        fun test2() {
            // given
            val articleId = 15555L

            // when
            // then
            val findArticleDislikeCount = articleLikeArticleDislikeCountPersistenceAdapter.findByIdOrNull(articleId)

            assertThat(findArticleDislikeCount).isNull()
        }
    }
}
