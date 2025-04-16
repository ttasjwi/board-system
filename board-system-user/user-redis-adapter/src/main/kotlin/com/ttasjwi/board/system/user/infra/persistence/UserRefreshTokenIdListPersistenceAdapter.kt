package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.user.domain.port.UserRefreshTokenIdListPersistencePort
import org.springframework.data.redis.connection.StringRedisConnection
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class UserRefreshTokenIdListPersistenceAdapter(
    private val redisTemplate: StringRedisTemplate
) : UserRefreshTokenIdListPersistencePort {

    companion object {
        // board-system::user::{userId}::refresh-token-ids
        private const val KEY_FORMAT = "board-system::user::%s::refresh-token-ids"
    }

    override fun add(userId: Long, refreshTokenId: Long, limit: Long) {
        redisTemplate.executePipelined {
            val conn = it as StringRedisConnection
            val key = generateKey(userId)
            conn.zAdd(key, 0.0, toPaddedString(refreshTokenId)) // 동일한 score 일 경우 value 값에 의해 정렬 상태가 만들어짐
            conn.zRemRange(key, 0, -limit - 1) // 최신의 limit 건만 유지되도록 함
            null
        }
    }

    override fun findAll(userId: Long): List<Long> {
        val key = generateKey(userId)
        return redisTemplate.opsForZSet()
            .range(key, 0, -1)!!.map { it.toLong() }
    }

    override fun remove(userId: Long, refreshTokenId: Long) {
        val key = generateKey(userId)
        redisTemplate.opsForZSet()
            .remove(key, toPaddedString(refreshTokenId))
    }

    override fun exists(userId: Long, refreshTokenId: Long): Boolean {
        val key = generateKey(userId)
        val rank = redisTemplate.opsForZSet()
            .rank(key, toPaddedString(refreshTokenId))
        return rank != null
    }

    private fun generateKey(userId: Long): String {
        return KEY_FORMAT.format(userId)
    }

    /**
     * Redis 에 RefreshTokenId 를 Value 로 등록할 때 문자열로 저장해야함.
     * 숫자 기준으로 정렬을 할 수 있도록 앞에 패딩을 붙임
     */
    private fun toPaddedString(refreshTokenId: Long): String {
        return "%019d".format(refreshTokenId)
    }
}
