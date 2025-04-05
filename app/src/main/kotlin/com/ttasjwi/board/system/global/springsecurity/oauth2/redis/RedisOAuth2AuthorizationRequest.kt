package com.ttasjwi.board.system.global.springsecurity.oauth2.redis

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

class RedisOAuth2AuthorizationRequest(
    val authorizationUri: String,
    val authorizationGrantType: String,
    val responseType: String,
    val clientId: String,
    val redirectUri: String,
    val scopes: Set<String>,
    val state: String,
    val additionalParameters: Map<String, Any>,
    val authorizationRequestUri: String,
    val attributes: Map<String, Any>
) {

    companion object {
        fun from(oauth2AuthorizationRequest: OAuth2AuthorizationRequest): RedisOAuth2AuthorizationRequest {
            return RedisOAuth2AuthorizationRequest(
                authorizationUri = oauth2AuthorizationRequest.authorizationUri,
                authorizationGrantType = oauth2AuthorizationRequest.grantType.value,
                responseType = oauth2AuthorizationRequest.responseType.value,
                clientId = oauth2AuthorizationRequest.clientId,
                redirectUri = oauth2AuthorizationRequest.redirectUri,
                scopes = oauth2AuthorizationRequest.scopes,
                state = oauth2AuthorizationRequest.state,
                additionalParameters = oauth2AuthorizationRequest.additionalParameters,
                authorizationRequestUri = oauth2AuthorizationRequest.authorizationRequestUri,
                attributes = oauth2AuthorizationRequest.attributes
            )
        }
    }


    fun toOAuth2AuthorizationRequest(): OAuth2AuthorizationRequest {
        return OAuth2AuthorizationRequest.authorizationCode()
            .authorizationUri(this.authorizationUri)
            .clientId(this.clientId)
            .redirectUri(this.redirectUri)
            .scopes(this.scopes)
            .state(this.state)
            .additionalParameters(this.additionalParameters)
            .authorizationRequestUri(this.authorizationRequestUri)
            .attributes(this.attributes)
            .build()
    }
}
