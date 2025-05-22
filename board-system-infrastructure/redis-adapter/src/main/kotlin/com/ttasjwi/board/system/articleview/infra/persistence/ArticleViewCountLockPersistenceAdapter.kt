package com.ttasjwi.board.system.articleview.infra.persistence

import com.ttasjwi.board.system.articleview.domain.port.ArticleViewCountLockPersistencePort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class ArticleViewCountLockPersistenceAdapter(
    private val redisTemplate: StringRedisTemplate
) : ArticleViewCountLockPersistencePort {

    companion object {
        private const val KEY_PATTERN = "board-system::article-view::article::%s::user::%s::lock"
        private const val LOCK_VALUE = ""
    }

    override fun lock(articleId: Long, userId: Long, ttl: Duration): Boolean {
        val key = generateKey(articleId, userId)
        return redisTemplate.opsForValue().setIfAbsent(key, LOCK_VALUE, ttl)!!
    }

    private fun generateKey(articleId: Long, userId: Long): String {
        return KEY_PATTERN.format(articleId, userId)
    }
}
