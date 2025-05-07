package com.ttasjwi.board.system.user.domain.port

import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration

interface OAuth2AccessTokenClientPort {

    fun authenticate(
        code: String,
        oAuth2ClientRegistration: OAuth2ClientRegistration,
        oAuth2AuthorizationRequest: OAuth2AuthorizationRequest
    ): OAuth2AccessTokenResponse
}

data class OAuth2AccessTokenResponse(
    val accessToken: String,
    val idToken: String?,
)
