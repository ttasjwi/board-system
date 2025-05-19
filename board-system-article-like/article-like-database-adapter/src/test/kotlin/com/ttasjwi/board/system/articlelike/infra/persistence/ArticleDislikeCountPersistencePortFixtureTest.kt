package com.ttasjwi.board.system.articlelike.infra.persistence

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleDislikeCountFixture
import com.ttasjwi.board.system.articlelike.infra.test.ArticleLikeDataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-like-database-adapter] ArticleDislikeCountPersistenceAdapter 테스트")
class ArticleDislikeCountPersistencePortFixtureTest : ArticleLikeDataBaseIntegrationTest() {

    @Nested
    @DisplayName("findByIdOrNull : articleId 값으로 좋아요 수 조회")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("성공 테스트")
        fun test1() {
            // given
            val articleId = 13L
            val articleDislikeCount = articleDislikeCountFixture(
                articleId = articleId,
                dislikeCount = 1345L
            )
            articleLikeArticleDislikeCountPersistenceAdapter.save(articleDislikeCount)

            // when
            val findArticleDislikeCount = articleLikeArticleDislikeCountPersistenceAdapter.findByIdOrNull(articleId)!!

            // then
            assertThat(findArticleDislikeCount.articleId).isEqualTo(articleDislikeCount.articleId)
            assertThat(findArticleDislikeCount.dislikeCount).isEqualTo(articleDislikeCount.dislikeCount)
        }


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
