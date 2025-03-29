package com.ttasjwi.board.system.auth.domain.external.impl

import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenHolderAppender
import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenHolderFinder
import com.ttasjwi.board.system.auth.domain.external.impl.redis.RedisRefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class ExternalRefreshTokenHolderStorageImpl(
    private val redisTemplate: RedisTemplate<String, RedisRefreshTokenHolder>,
) : ExternalRefreshTokenHolderAppender, ExternalRefreshTokenHolderFinder {

    companion object {

        // Board-System:RefreshTokenHolder:{memberId}
        private const val KEY_PREFIX = "Board-System:RefreshTokenHolder:"
    }

    override fun append(memberId: Long, refreshTokenHolder: RefreshTokenHolder, expiresAt: ZonedDateTime) {
        val key = generateKey(memberId)
        val redisModel = RedisRefreshTokenHolder.from(refreshTokenHolder)

        redisTemplate.opsForValue().set(key, redisModel)
        redisTemplate.expireAt(key, expiresAt.toInstant())
    }

    override fun findByMemberIdOrNull(memberId: Long): RefreshTokenHolder? {
        val key = generateKey(memberId)
        return redisTemplate.opsForValue().get(key)?.restoreDomain()
    }

    override fun removeByMemberId(memberId: Long) {
        val key = generateKey(memberId)
        redisTemplate.delete(key)
    }

    private fun generateKey(memberId: Long): String {
        return KEY_PREFIX + memberId.toString()
    }
}
