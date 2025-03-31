package com.ttasjwi.board.system.auth.domain.external.impl.redis

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.serializer.RedisSerializer

class RedisRefreshTokenHolderSerializer(
    private val objectMapper: ObjectMapper
) : RedisSerializer<RedisRefreshTokenHolder> {

    override fun serialize(value: RedisRefreshTokenHolder?): ByteArray? {
        return objectMapper.writeValueAsBytes(value)
    }

    override fun deserialize(bytes: ByteArray?): RedisRefreshTokenHolder? {
        if (bytes == null) return null
        return objectMapper.readValue(bytes, RedisRefreshTokenHolder::class.java)
    }
}
