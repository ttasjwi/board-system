package com.ttasjwi.board.system.user.domain.service

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.model.OAuth2UserPrincipal
import com.ttasjwi.board.system.user.domain.port.OAuth2AccessTokenClientPort
import com.ttasjwi.board.system.user.domain.port.OAuth2UserPrincipalClientPort

@DomainService
class OAuth2UserAuthenticator(
    private val oAuth2AccessTokenClientPort: OAuth2AccessTokenClientPort,
    private val oAuth2UserPrincipalClientPort: OAuth2UserPrincipalClientPort,
) {

    fun authenticate(code: String, oAuth2ClientRegistration: OAuth2ClientRegistration, oAuth2AuthorizationRequest: OAuth2AuthorizationRequest): OAuth2UserPrincipal {
        // 토큰 엔드포인트 통신하여 액세스토큰을 얻어옴
        val accessTokenResponse = oAuth2AccessTokenClientPort.authenticate(code, oAuth2ClientRegistration, oAuth2AuthorizationRequest)

        // 사용자 정보 엔드포인트와 통신하여, 사용자 정보(소셜서비스 식별자, 사용자 아이디, 이메일)를 획득해옴)
        return oAuth2UserPrincipalClientPort.fetch(accessTokenResponse.accessToken, oAuth2ClientRegistration)
    }
}
