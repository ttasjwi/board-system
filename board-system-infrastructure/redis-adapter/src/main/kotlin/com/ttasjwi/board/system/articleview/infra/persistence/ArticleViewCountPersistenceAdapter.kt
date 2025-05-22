package com.ttasjwi.board.system.articleview.infra.persistence

import com.ttasjwi.board.system.articleview.domain.model.ArticleViewCount
import com.ttasjwi.board.system.articleview.domain.port.ArticleViewCountPersistencePort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class ArticleViewCountPersistenceAdapter(
    private val redisTemplate: StringRedisTemplate
) : ArticleViewCountPersistencePort {

    companion object {
        private const val KEY_PATTERN = "board-system::article-view::article::%s::view-count"
    }

    override fun increase(articleId: Long) {
        val key = generateKey(articleId)
        redisTemplate.opsForValue().increment(key)
    }

    override fun findByIdOrNull(articleId: Long): ArticleViewCount? {
        val key = generateKey(articleId)
        val viewCount = redisTemplate.opsForValue().get(key)?.toLong() ?: return null

        return ArticleViewCount.restore(
            articleId = articleId,
            viewCount = viewCount
        )
    }

    private fun generateKey(articleId: Long): String {
        return KEY_PATTERN.format(articleId)
    }
}
