package com.ttasjwi.board.system.articleview.infra.persistence

import com.ttasjwi.board.system.test.RedisAdapterTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-view-redis-adapter] ArticleViewCountPersistenceAdapter 테스트")
class ArticleViewCountPersistencePortFixtureTest : RedisAdapterTest() {

    @BeforeEach
    fun tearDown() {
        val keys = redisTemplate.keys("board-system::article-view::article::*::view-count")
        if (keys.isNotEmpty()) {
            redisTemplate.delete(keys)
        }
    }

    @Test
    @DisplayName("조회수 증가 후 게시글 Id로 조회 테스트")
    fun increaseAndFindByIdOrNullTest() {
        // given
        val articleId = 123153L
        articleViewCountPersistenceAdapter.increase(articleId)

        // when
        val articleViewCount = articleViewCountPersistenceAdapter.findByIdOrNull(articleId)!!

        // then
        assertThat(articleViewCount.articleId).isEqualTo(articleId)
        assertThat(articleViewCount.viewCount).isEqualTo(1)
    }


    @Test
    @DisplayName("조회수 2회 증가 후 게시글 Id로 조회 테스트")
    fun doubleIncreaseAndFindByIdOrNullTest() {
        // given
        val articleId = 123153L
        articleViewCountPersistenceAdapter.increase(articleId)
        articleViewCountPersistenceAdapter.increase(articleId)

        // when
        val articleViewCount = articleViewCountPersistenceAdapter.findByIdOrNull(articleId)!!

        // then
        assertThat(articleViewCount.articleId).isEqualTo(articleId)
        assertThat(articleViewCount.viewCount).isEqualTo(2)
    }


    @Test
    @DisplayName("조회수가 저장되어 있지않은 게시글 Id로 조회 시 null 반환")
    fun notFoundTest() {
        // given
        val articleId = 12143125L

        // when
        val articleViewCount = articleViewCountPersistenceAdapter.findByIdOrNull(articleId)

        // then
        assertThat(articleViewCount).isNull()
    }
}
