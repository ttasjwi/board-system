package com.ttasjwi.board.system.member.infra.persistence

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.port.RefreshTokenIdPersistencePort
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RefreshTokenIdPersistenceAdapter(
    private val redisTemplate: RedisTemplate<String, String>
) : RefreshTokenIdPersistencePort {

    companion object {

        // board-system::member::{memberId}::refresh-token::{refreshTokenId}
        private const val KEY_FORMAT = "board-system::member::%s::refresh-token::%s"
    }

    override fun save(memberId: Long, refreshTokenId: Long, expiresAt: AppDateTime) {
        val key = generateKey(memberId, refreshTokenId)
        redisTemplate.opsForValue().set(key, refreshTokenId.toString())
        redisTemplate.expireAt(key, expiresAt.toInstant())
    }

    override fun exists(memberId: Long, refreshTokenId: Long): Boolean {
        return redisTemplate.hasKey(generateKey(memberId, refreshTokenId))
    }

    override fun remove(memberId: Long, refreshTokenId: Long) {
        redisTemplate.delete(generateKey(memberId, refreshTokenId))
    }

    private fun generateKey(memberId: Long, refreshTokenId: Long): String {
        return KEY_FORMAT.format(memberId, refreshTokenId)
    }
}
