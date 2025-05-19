package com.ttasjwi.board.system.articlelike.domain.port.fixture

import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleDislikeCountFixture
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
            articleDislikeCountPersistencePortFixture.save(articleDislikeCount)

            // when
            val findArticleDislikeCount = articleDislikeCountPersistencePortFixture.findByIdOrNull(articleId)!!

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
            val findArticleDislikeCount = articleDislikeCountPersistencePortFixture.findByIdOrNull(articleId)

            assertThat(findArticleDislikeCount).isNull()
        }
    }
}
