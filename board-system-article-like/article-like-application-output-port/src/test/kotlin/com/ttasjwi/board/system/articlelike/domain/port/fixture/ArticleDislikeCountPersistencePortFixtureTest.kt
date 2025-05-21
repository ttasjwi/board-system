package com.ttasjwi.board.system.articlelike.domain.port.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-like-application-output-port] ArticleDislikeCountPersistencePortFixture 테스트")
class ArticleDislikeCountPersistencePortFixtureTest {

    private lateinit var articleDislikeCountPersistencePortFixture: ArticleDislikeCountPersistencePortFixture

    @BeforeEach
    fun setup() {
        articleDislikeCountPersistencePortFixture = ArticleDislikeCountPersistencePortFixture()
    }

    @Nested
    @DisplayName("increase: 싫어요 수 증가")
    inner class IncreaseTest {

        @Test
        @DisplayName("최초 싫어요 테스트")
        fun test1() {
            // given
            val articleId = 5857L

            // when
            articleDislikeCountPersistencePortFixture.increase(articleId)


            // then
            val articleDislikeCount = articleDislikeCountPersistencePortFixture.findByIdOrNull(articleId)!!

            assertThat(articleDislikeCount.articleId).isEqualTo(articleId)
            assertThat(articleDislikeCount.dislikeCount).isEqualTo(1)
        }

        @Test
        @DisplayName("첫번째 이후 싫어요 테스트")
        fun test2() {
            // given
            val articleId = 5857L

            // when
            articleDislikeCountPersistencePortFixture.increase(articleId)
            articleDislikeCountPersistencePortFixture.increase(articleId)

            // then
            val articleDislikeCount = articleDislikeCountPersistencePortFixture.findByIdOrNull(articleId)!!

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
            articleDislikeCountPersistencePortFixture.increase(articleId)
            articleDislikeCountPersistencePortFixture.increase(articleId)
            articleDislikeCountPersistencePortFixture.decrease(articleId)

            // then
            val articleDislikeCount = articleDislikeCountPersistencePortFixture.findByIdOrNull(articleId)!!

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
            val findArticleDislikeCount = articleDislikeCountPersistencePortFixture.findByIdOrNull(articleId)

            assertThat(findArticleDislikeCount).isNull()
        }
    }
}
