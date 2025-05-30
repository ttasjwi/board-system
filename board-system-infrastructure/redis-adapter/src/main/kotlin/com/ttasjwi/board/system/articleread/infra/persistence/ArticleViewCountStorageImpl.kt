package com.ttasjwi.board.system.articleread.infra.persistence

import com.ttasjwi.board.system.articleread.domain.port.ArticleViewCountStorage
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component("articleReadArticleViewCountStorageImpl")
class ArticleViewCountStorageImpl(
    private val redisTemplate: StringRedisTemplate
) : ArticleViewCountStorage {

    companion object {
        private const val KEY_PATTERN = "board-system::article-view::article::%s::view-count"
    }

    override fun readViewCount(articleId: Long): Long {
        val key = generateKey(articleId)
        return redisTemplate.opsForValue().get(key)?.toLong() ?: return 0L
    }

    override fun readViewCounts(articleIds: List<Long>): Map<Long, Long> {
        if (articleIds.isEmpty()) return emptyMap()

        val keys = articleIds.map { generateKey(it) }
        val values = redisTemplate.opsForValue().multiGet(keys)

        return articleIds.mapIndexed { index, id ->
            val count = values?.getOrNull(index)?.toLong() ?: 0L
            id to count
        }.toMap()
    }

    private fun generateKey(articleId: Long): String {
        return KEY_PATTERN.format(articleId)
    }
}
