package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.port.RefreshTokenIdPersistencePort
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RefreshTokenIdPersistenceAdapter(
    private val redisTemplate: RedisTemplate<String, String>
) : RefreshTokenIdPersistencePort {

    companion object {

        // board-system::user::{userId}::refresh-token::{refreshTokenId}
        private const val KEY_FORMAT = "board-system::user::%s::refresh-token::%s"
    }

    override fun save(userId: Long, refreshTokenId: Long, expiresAt: AppDateTime) {
        val key = generateKey(userId, refreshTokenId)
        redisTemplate.opsForValue().set(key, refreshTokenId.toString())
        redisTemplate.expireAt(key, expiresAt.toInstant())
    }

    override fun exists(userId: Long, refreshTokenId: Long): Boolean {
        return redisTemplate.hasKey(generateKey(userId, refreshTokenId))
    }

    override fun remove(userId: Long, refreshTokenId: Long) {
        redisTemplate.delete(generateKey(userId, refreshTokenId))
    }

    private fun generateKey(userId: Long, refreshTokenId: Long): String {
        return KEY_FORMAT.format(userId, refreshTokenId)
    }
}
