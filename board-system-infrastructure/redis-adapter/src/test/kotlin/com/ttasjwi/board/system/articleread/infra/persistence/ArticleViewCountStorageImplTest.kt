package com.ttasjwi.board.system.articleread.infra.persistence

import com.ttasjwi.board.system.test.RedisAdapterTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-read-redis-adapter] ArticleViewCountStorageImpl 테스트")
class ArticleViewCountStorageImplTest : RedisAdapterTest() {

    companion object {
        private const val KEY_PATTERN = "board-system::article-view::article::%s::view-count"
    }

    @BeforeEach
    fun tearDown() {
        val keys = redisTemplate.keys("board-system::article-view::article::*::view-count")
        if (keys.isNotEmpty()) {
            redisTemplate.delete(keys)
        }
    }

    @Test
    @DisplayName("조회수 저장 후 조회 성공 테스트")
    fun readSuccessTest() {
        // given
        val articleId = 123153L
        val redisKey = generateRedisKey(articleId)
        val savedViewCount = 8L
        redisTemplate.opsForValue().set(redisKey, savedViewCount.toString())

        // when
        val findViewCount = articleReadArticleViewCountStorageImpl.readViewCount(articleId)

        // then
        assertThat(findViewCount).isEqualTo(savedViewCount)
    }


    @Test
    @DisplayName("조회수가 저장되어 있지않은 게시글 Id로 조회 시 조회수는 0이 반환된다.")
    fun notFoundTest() {
        // given
        val articleId = 12143125L

        // when
        val viewCount = articleReadArticleViewCountStorageImpl.readViewCount(articleId)

        // then
        assertThat(viewCount).isEqualTo(0L)
    }

    private fun generateRedisKey(articleId: Long): String {
        return KEY_PATTERN.format(articleId)
    }
}
