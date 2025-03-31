package com.ttasjwi.board.system.member.domain.external.db.redis

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.serializer.RedisSerializer

class RedisEmailVerificationSerializer(
    private val objectMapper: ObjectMapper
) : RedisSerializer<RedisEmailVerification> {

    override fun serialize(value: RedisEmailVerification?): ByteArray? {
        return objectMapper.writeValueAsBytes(value)
    }

    override fun deserialize(bytes: ByteArray?): RedisEmailVerification? {
        if (bytes == null) return null
        return objectMapper.readValue(bytes, RedisEmailVerification::class.java)
    }
}
