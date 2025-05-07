package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.common.dataserializer.DataSerializer
import com.ttasjwi.board.system.common.logger.getLogger
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.port.OAuth2AuthorizationRequestPersistencePort
import com.ttasjwi.board.system.user.infra.persistence.redis.RedisOAuth2AuthorizationRequest
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class OAuth2AuthorizationRequestPersistenceAdapter(
    private val redisTemplate: StringRedisTemplate
) : OAuth2AuthorizationRequestPersistencePort {

    companion object {
        // board-system::user::oauth2-authorization-request::{state}
        private const val KEY_FORMAT = "board-system::user::oauth2-authorization-request::%s"

        private val log = getLogger(OAuth2AuthorizationRequestPersistenceAdapter::class.java)
    }

    override fun save(authorizationRequest: OAuth2AuthorizationRequest, expiresAt: AppDateTime) {
        val key = generateKey(authorizationRequest.state)
        val data = RedisOAuth2AuthorizationRequest.from(authorizationRequest)

        redisTemplate.opsForValue().set(key, DataSerializer.serialize(data))
        redisTemplate.expireAt(key, expiresAt.toInstant())

        log.info{ "Successfully saved authorization request: key = $key" }
    }

    override fun read(state: String): OAuth2AuthorizationRequest? {
        val key = generateKey(state)
        val findData = redisTemplate.opsForValue().get(key)

        if (findData == null) {
            log.info { "OAuth2Authorization Not Found : key = $key" }
            return null
        }

        return DataSerializer.deserialize(findData, RedisOAuth2AuthorizationRequest::class.java).restoreDomain()
    }

    override fun remove(state: String): OAuth2AuthorizationRequest? {
        val oAuth2AuthorizationRequest = read(state) ?: return null
        val key = generateKey(oAuth2AuthorizationRequest.state)
        redisTemplate.delete(key)
        log.info{ "Successfully removed authorization request: key = $key" }

        return oAuth2AuthorizationRequest
    }

    private fun generateKey(state: String): String {
        return KEY_FORMAT.format(state)
    }
}
