package com.ttasjwi.board.system.user.infra.oauth2

import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.model.OAuth2UserPrincipal
import com.ttasjwi.board.system.user.domain.port.OAuth2UserPrincipalClientPort
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class OAuth2UserPrincipalClientAdapter : OAuth2UserPrincipalClientPort {

    private val restClient: RestClient = RestClient.create()

    override fun fetch(accessToken: String, oAuth2ClientRegistration: OAuth2ClientRegistration): OAuth2UserPrincipal {
        val userInfoUri = oAuth2ClientRegistration.providerDetails.userInfoEndpoint.uri
        val headers = requestHeaders(accessToken)

        val response = restClient.get()
            .uri(userInfoUri)
            .headers { it.addAll(headers) }
            .retrieve()
            .body(Map::class.java) as Map<*, *>
        return mapToOAuth2UserPrincipal(response, oAuth2ClientRegistration)
    }

    private fun requestHeaders(accessToken: String): HttpHeaders {
        val headers = HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
        return headers
    }

    private fun mapToOAuth2UserPrincipal(
        response: Map<*, *>,
        oAuth2ClientRegistration: OAuth2ClientRegistration
    ): OAuth2UserPrincipal {
        if (oAuth2ClientRegistration.registrationId == "naver") {
            return OAuth2UserPrincipal(
                socialServiceName = oAuth2ClientRegistration.registrationId,
                socialServiceUserId = (response["response"] as Map<*, *>)["id"] as String,
                email = (response["response"] as Map<*, *>)["email"] as String
            )
        }
        throw IllegalStateException("OAuth2 방식 사용자 정보 획득은 naver 만 지원됨")
    }
}
