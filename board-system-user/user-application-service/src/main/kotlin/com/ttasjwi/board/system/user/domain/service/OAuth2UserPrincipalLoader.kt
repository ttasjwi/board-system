package com.ttasjwi.board.system.user.domain.service

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.model.OAuth2UserPrincipal
import com.ttasjwi.board.system.user.domain.port.OAuth2AccessTokenClientPort
import com.ttasjwi.board.system.user.domain.port.OAuth2UserPrincipalClientPort
import com.ttasjwi.board.system.user.domain.port.OidcOAuth2UserPrincipalPort

@DomainService
class OAuth2UserPrincipalLoader(
    private val oAuth2AccessTokenClientPort: OAuth2AccessTokenClientPort,
    private val oAuth2UserPrincipalClientPort: OAuth2UserPrincipalClientPort,
    private val oidcOAuth2UserPrincipalPort: OidcOAuth2UserPrincipalPort,
) {

    fun getOAuth2UserPrincipal(
        code: String,
        oAuth2ClientRegistration: OAuth2ClientRegistration,
        oAuth2AuthorizationRequest: OAuth2AuthorizationRequest
    ): OAuth2UserPrincipal {

        // 토큰 엔드포인트와 통신하여, 액세스토큰을 얻어옴. (oidc 의 경우 IdToken 도 얻어와짐)
        val accessTokenResponse = oAuth2AccessTokenClientPort.authenticate(
            code = code,
            oAuth2ClientRegistration = oAuth2ClientRegistration,
            oAuth2AuthorizationRequest = oAuth2AuthorizationRequest
        )

        // scope에 "openid"가 없는 경우 사용자 정보 엔드포인트와 통신하여 사용자 정보 획득
        if (!oAuth2ClientRegistration.scopes.contains("openid")) {
            return oAuth2UserPrincipalClientPort.fetch(accessTokenResponse.accessToken, oAuth2ClientRegistration)
        }

        // 여기서부터 oidc 방식
        // 응답에 idToken 이 없으면 예외 발생 (서버 에러)
        if (accessTokenResponse.idToken == null) {
            throw IllegalStateException("Oidc - IdToken not found from OAuth2AccessTokenResponse")
        }

        // IdToken 을 통해 사용자 정보 획득
        return oidcOAuth2UserPrincipalPort.getUserPrincipal(accessTokenResponse.idToken!!, oAuth2ClientRegistration, oAuth2AuthorizationRequest)
    }
}
