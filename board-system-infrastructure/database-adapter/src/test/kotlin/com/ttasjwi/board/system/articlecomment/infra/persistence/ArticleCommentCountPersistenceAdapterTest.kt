package com.ttasjwi.board.system.articlecomment.infra.persistence

import com.ttasjwi.board.system.test.DataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-database-adapter] ArticleCommentCountPersistenceAdapter 테스트")
class ArticleCommentCountPersistenceAdapterTest : DataBaseIntegrationTest() {

    @Nested
    @DisplayName("increase: 댓글 수 증가")
    inner class IncreaseTest {

        @Test
        @DisplayName("최초 댓글수 증가 테스트")
        fun test1() {
            // given
            val articleId = 5857L

            // when
            articleCommentArticleCommentCountPersistenceAdapter.increase(articleId)


            // then
            val articleCommentCount = articleCommentArticleCommentCountPersistenceAdapter.findByIdOrNull(articleId)!!

            assertThat(articleCommentCount.articleId).isEqualTo(articleId)
            assertThat(articleCommentCount.commentCount).isEqualTo(1)
        }

        @Test
        @DisplayName("첫번째 이후 댓글수 증가 테스트")
        fun test2() {
            // given
            val articleId = 5857L

            // when
            articleCommentArticleCommentCountPersistenceAdapter.increase(articleId)
            articleCommentArticleCommentCountPersistenceAdapter.increase(articleId)

            // then
            val articleCommentCount = articleCommentArticleCommentCountPersistenceAdapter.findByIdOrNull(articleId)!!

            assertThat(articleCommentCount.articleId).isEqualTo(articleId)
            assertThat(articleCommentCount.commentCount).isEqualTo(2)
        }
    }


    @Nested
    @DisplayName("decrease: 댓글 수 감소")
    inner class DecreaseTest {

        @Test
        @DisplayName("두번 댓글 수 증가 후, 댓글수 감소 테스트")
        fun test() {
            // given
            val articleId = 5857L

            // when
            articleCommentArticleCommentCountPersistenceAdapter.increase(articleId)
            articleCommentArticleCommentCountPersistenceAdapter.increase(articleId)
            articleCommentArticleCommentCountPersistenceAdapter.decrease(articleId)

            // then
            val articleCommentCount = articleCommentArticleCommentCountPersistenceAdapter.findByIdOrNull(articleId)!!

            assertThat(articleCommentCount.articleId).isEqualTo(articleId)
            assertThat(articleCommentCount.commentCount).isEqualTo(1)
        }

    }


    @Nested
    @DisplayName("findByIdOrNull : articleId 값으로 댓글 수 조회")
    inner class FindByIdOrNullTest {

        @Test
        @DisplayName("조회 실패시 null 반환 테스트")
        fun test2() {
            // given
            val articleId = 15555L

            // when
            // then
            val findArticleCommentCount = articleCommentArticleCommentCountPersistenceAdapter.findByIdOrNull(articleId)

            assertThat(findArticleCommentCount).isNull()
        }
    }
}
