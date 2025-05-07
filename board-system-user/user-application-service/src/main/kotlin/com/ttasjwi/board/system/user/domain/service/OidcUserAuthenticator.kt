package com.ttasjwi.board.system.user.domain.service

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.model.OAuth2UserPrincipal
import com.ttasjwi.board.system.user.domain.port.OAuth2AccessTokenClientPort
import com.ttasjwi.board.system.user.domain.port.OidcOAuth2UserPrincipalPort

@DomainService
class OidcUserAuthenticator(
    private val oAuth2AccessTokenClientPort: OAuth2AccessTokenClientPort,
    private val oidcOAuth2UserPrincipalPort: OidcOAuth2UserPrincipalPort,
) {

    fun authenticate(
        code: String,
        oAuth2ClientRegistration: OAuth2ClientRegistration,
        oAuth2AuthorizationRequest: OAuth2AuthorizationRequest
    ): OAuth2UserPrincipal {

        val oAuth2AccessTokenResponse =
            oAuth2AccessTokenClientPort.authenticate(code, oAuth2ClientRegistration, oAuth2AuthorizationRequest)

        val idToken = oAuth2AccessTokenResponse.idToken
            ?: throw IllegalStateException("IdToken not found from OAuth2AccessTokenResponse")

        return oidcOAuth2UserPrincipalPort.getUserPrincipal(
            idToken,
            oAuth2ClientRegistration,
            oAuth2AuthorizationRequest
        )
    }
}
