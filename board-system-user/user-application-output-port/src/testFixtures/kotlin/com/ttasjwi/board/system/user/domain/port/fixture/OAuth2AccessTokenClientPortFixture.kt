package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.port.OAuth2AccessTokenClientPort
import com.ttasjwi.board.system.user.domain.port.OAuth2AccessTokenResponse

class OAuth2AccessTokenClientPortFixture(
    private var accessToken: String = "access-token",
    private var idToken: String? = "idToken",
) : OAuth2AccessTokenClientPort {

    override fun authenticate(
        code: String,
        oAuth2ClientRegistration: OAuth2ClientRegistration,
        oAuth2AuthorizationRequest: OAuth2AuthorizationRequest
    ): OAuth2AccessTokenResponse {

        return OAuth2AccessTokenResponse(
            accessToken = accessToken,
            idToken = idToken
        )
    }

    fun changeAccessToken(accessToken: String) {
        this.accessToken = accessToken
    }

    fun changeIdToken(idToken: String?) {
        this.idToken = idToken
    }
}
