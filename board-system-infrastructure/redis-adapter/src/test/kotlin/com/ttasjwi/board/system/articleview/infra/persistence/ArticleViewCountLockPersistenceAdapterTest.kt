package com.ttasjwi.board.system.articleview.infra.persistence

import com.ttasjwi.board.system.test.RedisAdapterTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.Duration

@DisplayName("[article-view-redis-adapter] ArticleViewCountLockPersistenceAdapter 테스트")
class ArticleViewCountLockPersistenceAdapterTest : RedisAdapterTest() {

    @AfterEach
    fun tearDown() {
        val keys = redisTemplate.keys("board-system::article-view::article::*::user::*::lock")
        if (keys.isNotEmpty()) {
            redisTemplate.delete(keys)
        }
    }

    @Test
    @DisplayName("이미 동일 사용자가 락을 획득해서, 락 획득에 실패할 경우, False 반환")
    fun test1() {
        // given
        val articleId = 1234L
        val userId = 123456L
        val ttl = Duration.ofMinutes(10)

        val firstLockSuccess = articleViewCountLockPersistenceAdapter.lock(articleId, userId, ttl)
        val secondLockSuccess = articleViewCountLockPersistenceAdapter.lock(articleId, userId, ttl)

        // when
        assertThat(firstLockSuccess).isTrue
        assertThat(secondLockSuccess).isFalse
    }

    @Test
    @DisplayName("사용자가 락을 획득하지 않았다면 true 반환")
    fun test2() {
        // given
        val articleId = 1234L
        val userId = 21324134L
        val ttl = Duration.ofMinutes(10)

        // when
        val getLockSuccess = articleViewCountLockPersistenceAdapter.lock(articleId, userId, ttl)

        // then
        assertThat(getLockSuccess).isTrue()
    }
}
