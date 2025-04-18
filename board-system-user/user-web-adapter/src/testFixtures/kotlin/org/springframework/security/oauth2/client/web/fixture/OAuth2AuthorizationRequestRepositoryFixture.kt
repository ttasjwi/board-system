package org.springframework.security.oauth2.client.web.fixture

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames

class OAuth2AuthorizationRequestRepositoryFixture : AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private val storage = mutableMapOf<String, OAuth2AuthorizationRequest>()

    companion object {
        private const val PREFIX = "Board-System:OAuth2AuthorizationRequest:"
    }

    override fun loadAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        val state = getStateParameter(request) ?: return null
        val key = makeKey(state)
        return storage[key]
    }

    override fun removeAuthorizationRequest(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): OAuth2AuthorizationRequest? {
        val oAuth2AuthorizationRequest = loadAuthorizationRequest(request) ?: return null

        val key = makeKey(oAuth2AuthorizationRequest.state)
        storage.remove(key)
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

        storage[key] = authorizationRequest
    }

    private fun getStateParameter(request: HttpServletRequest): String? {
        return request.getParameter(OAuth2ParameterNames.STATE)
    }

    private fun makeKey(state: String): String {
        return PREFIX + state
    }
}
