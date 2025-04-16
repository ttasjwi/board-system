package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.user.domain.port.MemberRefreshTokenIdListPersistencePort
import org.springframework.data.redis.connection.StringRedisConnection
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class MemberRefreshTokenIdListPersistenceAdapter(
    private val redisTemplate: StringRedisTemplate
) : MemberRefreshTokenIdListPersistencePort {

    companion object {
        // board-system::member::{memberId}::refresh-token-ids
        private const val KEY_FORMAT = "board-system::member::%s::refresh-token-ids"
    }

    override fun add(memberId: Long, refreshTokenId: Long, limit: Long) {
        redisTemplate.executePipelined {
            val conn = it as StringRedisConnection
            val key = generateKey(memberId)
            conn.zAdd(key, 0.0, toPaddedString(refreshTokenId)) // 동일한 score 일 경우 value 값에 의해 정렬 상태가 만들어짐
            conn.zRemRange(key, 0, -limit - 1) // 최신의 limit 건만 유지되도록 함
            null
        }
    }

    override fun findAll(memberId: Long): List<Long> {
        val key = generateKey(memberId)
        return redisTemplate.opsForZSet()
            .range(key, 0, -1)!!.map { it.toLong() }
    }

    override fun remove(memberId: Long, refreshTokenId: Long) {
        val key = generateKey(memberId)
        redisTemplate.opsForZSet()
            .remove(key, toPaddedString(refreshTokenId))
    }

    override fun exists(memberId: Long, refreshTokenId: Long): Boolean {
        val key = generateKey(memberId)
        val rank = redisTemplate.opsForZSet()
            .rank(key, toPaddedString(refreshTokenId))
        return rank != null
    }

    private fun generateKey(memberId: Long): String {
        return KEY_FORMAT.format(memberId)
    }

    /**
     * Redis 에 RefreshTokenId 를 Value 로 등록할 때 문자열로 저장해야함.
     * 숫자 기준으로 정렬을 할 수 있도록 앞에 패딩을 붙임
     */
    private fun toPaddedString(refreshTokenId: Long): String {
        return "%019d".format(refreshTokenId)
    }
}
