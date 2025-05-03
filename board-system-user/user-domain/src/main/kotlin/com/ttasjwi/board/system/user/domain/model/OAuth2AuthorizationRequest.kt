package com.ttasjwi.board.system.user.domain.model

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class OAuth2AuthorizationRequest
internal constructor(
    val authorizationUri: String,
    val oAuth2ClientRegistrationId: String,
    val responseType: OAuth2AuthorizationResponseType,
    val clientId: String,
    val redirectUri: String,
    val scopes: Set<String>,
    val state: String,
    val pkceParams: PKCEParams,
    val nonceParams: NonceParams?,
) {

    val authorizationRequestUri: String by lazy {
        val queryParams = listOf(
            "client_id" to clientId,
            "redirect_uri" to redirectUri,
            "response_type" to responseType.value,
            "scope" to scopes.joinToString(" "),
            "state" to state,
            "code_challenge" to pkceParams.codeChallenge,
            "code_challenge_method" to pkceParams.codeChallengeMethod,
        ) + (nonceParams?.let { listOf("nonce" to it.nonceHash) } ?: emptyList())

        buildUriWithQuery(authorizationUri, queryParams)
    }

    companion object {

        fun create(
            authorizationUri: String,
            oAuth2ClientRegistrationId: String,
            responseType: OAuth2AuthorizationResponseType,
            clientId: String,
            redirectUri: String,
            scopes: Set<String>,
            state: String,
            pkceParams: PKCEParams,
            nonceParams: NonceParams?,
        ): OAuth2AuthorizationRequest {
            return OAuth2AuthorizationRequest(
                authorizationUri = authorizationUri,
                oAuth2ClientRegistrationId = oAuth2ClientRegistrationId,
                responseType = responseType,
                clientId = clientId,
                redirectUri = redirectUri,
                scopes = scopes,
                state = state,
                pkceParams = pkceParams,
                nonceParams = nonceParams,
            )
        }
    }

    private fun buildUriWithQuery(base: String, queryParams: List<Pair<String, String>>): String {
        val queryString = queryParams.joinToString("&") { (k, v) ->
            "${encodeQueryParam(k)}=${encodeQueryParam(v)}"
        }
        return "$base?$queryString"
    }

    private fun encodeQueryParam(value: String): String {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
            .replace("+", "%20")
    }

    data class NonceParams(
        val nonce: String,
        val nonceHash: String,
    )

    data class PKCEParams(
        val codeChallenge: String,
        val codeChallengeMethod: String,
        val codeVerifier: String
    )
}
