package com.ttasjwi.board.system.user.infra.persistence.redis

import com.fasterxml.jackson.annotation.JsonProperty
import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest

class RedisOAuth2AuthorizationRequest(
    val authorizationUri: String,

    @field:JsonProperty("oAuth2ClientRegistrationId")
    val oAuth2ClientRegistrationId: String,
    val responseType: String,
    val clientId: String,
    val redirectUri: String,
    val scopes: Set<String>,
    val state: String,
    val codeChallenge: String,
    val codeChallengeMethod: String,
    val codeVerifier: String,
    val nonce: String?,
    val nonceHash: String?,
) {

    companion object {
        fun from(domain: OAuth2AuthorizationRequest): RedisOAuth2AuthorizationRequest {
            return RedisOAuth2AuthorizationRequest(
                authorizationUri = domain.authorizationUri,
                oAuth2ClientRegistrationId = domain.oAuth2ClientRegistrationId,
                responseType = domain.responseType.name,
                clientId = domain.clientId,
                redirectUri = domain.redirectUri,
                scopes = domain.scopes,
                state = domain.state,
                codeChallenge = domain.pkceParams.codeChallenge,
                codeChallengeMethod = domain.pkceParams.codeChallengeMethod,
                codeVerifier = domain.pkceParams.codeVerifier,
                nonce = domain.nonceParams?.nonce,
                nonceHash = domain.nonceParams?.nonceHash,
            )
        }
    }

    fun restoreDomain(): OAuth2AuthorizationRequest {
        return OAuth2AuthorizationRequest.restore(
            authorizationUri = authorizationUri,
            oAuth2ClientRegistrationId = oAuth2ClientRegistrationId,
            responseType = responseType,
            clientId = clientId,
            redirectUri = redirectUri,
            scopes = scopes,
            state = state,
            codeChallenge = codeChallenge,
            codeChallengeMethod = codeChallengeMethod,
            codeVerifier = codeVerifier,
            nonce = nonce,
            nonceHash = nonceHash,
        )
    }
}
