package com.ttasjwi.board.system.articleread.infra.persistence

import com.ttasjwi.board.system.test.RedisAdapterTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
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

    @Nested
    @DisplayName("readArticleViewCount: 게시글 조회수 조회")
    inner class ReadArticleViewCount {
        @Test
        @DisplayName("조회수 저장 후 조회 성공 테스트")
        fun readSuccessTest() {
            // given
            val articleId = 123153L
            val savedViewCount = 8L
            saveViewCount(articleId, savedViewCount)

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
    }

    @Nested
    @DisplayName("readArticleViewCounts: 복수의 게시글들의 조회수를 가져온다.")
    inner class ReadArticleViewCountsTest {

        @Test
        @DisplayName("게시글 아이디별 조회수를 Map 형태로 가져온다.")
        fun test1() {
            // given
            for (i in 1L..10L) {
                saveViewCount(articleId = i, viewCount = i)
            }

            // when
            val articleIds = (1L..10L).toList()

            // then
            val viewCounts = articleReadArticleViewCountStorageImpl.readViewCounts(articleIds)
            assertThat(viewCounts.size).isEqualTo(articleIds.size)
            for (i in 1L..10L) {
                assertThat(viewCounts[i]).isEqualTo(i)
            }
        }


        @Test
        @DisplayName("조회수가 없을 경우 0으로 가져와진다.")
        fun test2() {
            // given
            // when
            val articleIds = (1L..10L).toList()

            // then
            val viewCounts = articleReadArticleViewCountStorageImpl.readViewCounts(articleIds)
            assertThat(viewCounts.size).isEqualTo(articleIds.size)
            for (i in 1L..10L) {
                assertThat(viewCounts[i]).isEqualTo(0L)
            }
        }


        @Test
        @DisplayName("빈 리스트를 전달할 경우 빈 Map 이 반환된다.")
        fun test3() {
            // given
            // when
            val viewCounts = articleReadArticleViewCountStorageImpl.readViewCounts(emptyList())

            // then
            assertThat(viewCounts).isEmpty()
        }
    }

    private fun saveViewCount(articleId: Long, viewCount: Long) {
        val redisKey = generateRedisKey(articleId)
        redisTemplate.opsForValue().set(redisKey, viewCount.toString())
    }

    private fun generateRedisKey(articleId: Long): String {
        return KEY_PATTERN.format(articleId)
    }
}
