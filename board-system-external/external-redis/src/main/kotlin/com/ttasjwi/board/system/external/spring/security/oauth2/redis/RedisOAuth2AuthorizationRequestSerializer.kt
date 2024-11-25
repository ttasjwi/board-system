package com.ttasjwi.board.system.external.spring.security.oauth2.redis

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

class RedisOAuth2AuthorizationRequestSerializer(
    private val objectMapper: ObjectMapper
) : RedisSerializer<OAuth2AuthorizationRequest> {

    override fun serialize(value: OAuth2AuthorizationRequest?): ByteArray? {
        return objectMapper.writeValueAsBytes(value)
    }

    override fun deserialize(bytes: ByteArray?): OAuth2AuthorizationRequest? {
        if (bytes == null) return null
        return objectMapper.readValue(bytes, OAuth2AuthorizationRequest::class.java)
    }
}
