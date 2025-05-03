package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.common.dataserializer.DataSerializer
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.port.OAuth2AuthorizationRequestPersistencePort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class OAuth2AuthorizationRequestPersistenceAdapter(
    private val redisTemplate: StringRedisTemplate
) : OAuth2AuthorizationRequestPersistencePort {

    companion object {
        // board-system::user::oauth2-authorization-request::{state}
        private const val KEY_FORMAT = "board-system::user::oauth2-authorization-request::%s"
    }

    override fun save(authorizationRequest: OAuth2AuthorizationRequest, expiresAt: AppDateTime) {
        val key = generateKey(authorizationRequest.state)
        redisTemplate.opsForValue().set(key, DataSerializer.serialize(authorizationRequest))
        redisTemplate.expireAt(key, expiresAt.toInstant())
    }

    override fun read(state: String): OAuth2AuthorizationRequest? {
        val key = generateKey(state)
        return redisTemplate.opsForValue().get(key)
            ?.let { DataSerializer.deserialize(it, OAuth2AuthorizationRequest::class.java) }
    }

    override fun remove(state: String): OAuth2AuthorizationRequest? {
        val oAuth2AuthorizationRequest = read(state) ?: return null
        val key = generateKey(oAuth2AuthorizationRequest.state)
        redisTemplate.delete(key)
        return oAuth2AuthorizationRequest
    }

    private fun generateKey(state: String): String {
        return KEY_FORMAT.format(state)
    }
}
