package com.ttasjwi.board.system.user.infra.oauth2

import com.fasterxml.jackson.annotation.JsonProperty
import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientAuthenticationMethod
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.port.OAuth2AccessTokenClientPort
import com.ttasjwi.board.system.user.domain.port.OAuth2AccessTokenResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import java.util.Base64

@Component
class OAuth2AccessTokenClientAdapter : OAuth2AccessTokenClientPort {

    private val restClient: RestClient = RestClient.create()

    override fun authenticate(
        code: String,
        oAuth2ClientRegistration: OAuth2ClientRegistration,
        oAuth2AuthorizationRequest: OAuth2AuthorizationRequest
    ): OAuth2AccessTokenResponse {
        val tokenUri = oAuth2ClientRegistration.providerDetails.tokenUri
        val headers = defaultHeaders()
        val form = defaultForm(code, oAuth2AuthorizationRequest)
        handleHeadersAndForm(oAuth2ClientRegistration, headers, form)

        return restClient.post()
            .uri(tokenUri)
            .headers{ it.addAll(headers) }
            .body(form)
            .retrieve()
            .body<TokenResponse>()
            ?.mapToOAuth2AccessTokenResponse() ?: throw IllegalStateException("소셜서비스 인증 실패")
    }

    private fun handleHeadersAndForm(
        oAuth2ClientRegistration: OAuth2ClientRegistration,
        headers: HttpHeaders,
        form: MultiValueMap<String, String>
    ) {
        when (oAuth2ClientRegistration.clientAuthenticationMethod) {
            OAuth2ClientAuthenticationMethod.CLIENT_SECRET_BASIC -> {
                val creds = "${oAuth2ClientRegistration.clientId}:${oAuth2ClientRegistration.clientSecret}"
                val encoded = Base64.getEncoder().encodeToString(creds.toByteArray())
                headers.set(HttpHeaders.AUTHORIZATION, "Basic $encoded")
            }

            OAuth2ClientAuthenticationMethod.CLIENT_SECRET_POST -> {
                form.add("client_id", oAuth2ClientRegistration.clientId)
                form.add("client_secret", oAuth2ClientRegistration.clientSecret)
            }

            else -> throw IllegalArgumentException("지원하지 않는 client authentication method")
        }
    }

    private fun defaultForm(code: String, oAuth2AuthorizationRequest: OAuth2AuthorizationRequest): MultiValueMap<String, String> {
        val form = LinkedMultiValueMap<String, String>()
        form.add("grant_type", "authorization_code")
        form.add("code", code)
        form.add("redirect_uri", oAuth2AuthorizationRequest.redirectUri)
        form.add("code_verifier", oAuth2AuthorizationRequest.pkceParams.codeVerifier)
        return form
    }

    private fun defaultHeaders(): HttpHeaders {
        val headers =  HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        return headers
    }

    data class TokenResponse(
        @field:JsonProperty("access_token")
        val accessToken: String,

        @field:JsonProperty("id_token")
        val idToken: String?
    ) {

        fun mapToOAuth2AccessTokenResponse(): OAuth2AccessTokenResponse {
            return OAuth2AccessTokenResponse(
                accessToken = accessToken,
                idToken = idToken
            )
        }
    }
}
