package com.ttasjwi.board.system.external.spring.security.oauth2

import com.ttasjwi.board.system.external.spring.security.oauth2.redis.RedisOAuth2AuthorizationRequest
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisOAuth2AuthorizationRequestRepository(
    private val redisTemplate: RedisTemplate<String, RedisOAuth2AuthorizationRequest>
) : AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    companion object {
        private const val PREFIX = "Board-System:OAuth2AuthorizationRequest:"
        private const val VALIDITY_MINUTE = 5L
    }

    override fun loadAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        val state = getStateParameter(request) ?: return null
        val key = makeKey(state)
        return redisTemplate.opsForValue().get(key)?.toOAuth2AuthorizationRequest()
    }

    override fun removeAuthorizationRequest(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): OAuth2AuthorizationRequest? {
        val oAuth2AuthorizationRequest = loadAuthorizationRequest(request) ?: return null

        val key = makeKey(oAuth2AuthorizationRequest.state)
        redisTemplate.delete(key)
        return oAuth2AuthorizationRequest
    }

    override fun saveAuthorizationRequest(
        authorizationRequest: OAuth2AuthorizationRequest?,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        if (authorizationRequest == null) {
            removeAuthorizationRequest(request, response)
            return
        }
        val key = makeKey(authorizationRequest.state)
        val redisAuthorizationRequest = RedisOAuth2AuthorizationRequest.from(authorizationRequest)

        redisTemplate.opsForValue().set(key, redisAuthorizationRequest)
        redisTemplate.expire(key, Duration.ofMinutes(VALIDITY_MINUTE))
    }

    private fun getStateParameter(request: HttpServletRequest): String? {
        return request.getParameter(OAuth2ParameterNames.STATE)
    }

    private fun makeKey(state: String): String {
        return PREFIX + state
    }

}
