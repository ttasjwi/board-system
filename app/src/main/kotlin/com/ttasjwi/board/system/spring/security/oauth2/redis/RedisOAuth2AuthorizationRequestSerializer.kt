package com.ttasjwi.board.system.spring.security.oauth2.redis

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.serializer.RedisSerializer

class RedisOAuth2AuthorizationRequestSerializer(
    private val objectMapper: ObjectMapper
) : RedisSerializer<RedisOAuth2AuthorizationRequest> {

    override fun serialize(value: RedisOAuth2AuthorizationRequest?): ByteArray? {
        return objectMapper.writeValueAsBytes(value)
    }

    override fun deserialize(bytes: ByteArray?): RedisOAuth2AuthorizationRequest? {
        if (bytes == null) return null
        return objectMapper.readValue(bytes, RedisOAuth2AuthorizationRequest::class.java)
    }
}
