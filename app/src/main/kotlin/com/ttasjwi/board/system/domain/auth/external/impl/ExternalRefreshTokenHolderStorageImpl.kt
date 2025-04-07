package com.ttasjwi.board.system.domain.auth.external.impl

import com.ttasjwi.board.system.common.dataserializer.DataSerializer
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.auth.external.ExternalRefreshTokenHolderAppender
import com.ttasjwi.board.system.domain.auth.external.ExternalRefreshTokenHolderFinder
import com.ttasjwi.board.system.domain.auth.external.impl.redis.RedisRefreshTokenHolder
import com.ttasjwi.board.system.domain.auth.model.RefreshTokenHolder
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class ExternalRefreshTokenHolderStorageImpl(
    private val redisTemplate: StringRedisTemplate,
) : ExternalRefreshTokenHolderAppender, ExternalRefreshTokenHolderFinder {

    companion object {

        // Board-System:RefreshTokenHolder:{memberId}
        private const val KEY_PREFIX = "Board-System:RefreshTokenHolder:"
    }

    override fun append(memberId: Long, refreshTokenHolder: RefreshTokenHolder, expiresAt: AppDateTime) {
        val key = generateKey(memberId)
        val redisModel = RedisRefreshTokenHolder.from(refreshTokenHolder)

        redisTemplate.opsForValue().set(key, DataSerializer.serialize(redisModel))
        redisTemplate.expireAt(key, expiresAt.toInstant())
    }

    override fun findByMemberIdOrNull(memberId: Long): RefreshTokenHolder? {
        val key = generateKey(memberId)
        return redisTemplate.opsForValue().get(key)
            ?.let {
                DataSerializer.deserialize(it, RedisRefreshTokenHolder::class.java).restoreDomain()
            }
    }

    override fun removeByMemberId(memberId: Long) {
        val key = generateKey(memberId)
        redisTemplate.delete(key)
    }

    private fun generateKey(memberId: Long): String {
        return KEY_PREFIX + memberId.toString()
    }
}
