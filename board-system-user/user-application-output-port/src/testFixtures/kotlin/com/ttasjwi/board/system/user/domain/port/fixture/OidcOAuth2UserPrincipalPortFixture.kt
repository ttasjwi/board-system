package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.model.OAuth2UserPrincipal
import com.ttasjwi.board.system.user.domain.port.OidcOAuth2UserPrincipalPort

class OidcOAuth2UserPrincipalPortFixture(
    private var socialServiceName: String = "google",
    private var socialServiceUserId: String = "DEFAULT_USER_ID",
    private var email: String = "default@email.com"
) : OidcOAuth2UserPrincipalPort {

    var callCount = 0

    override fun getUserPrincipal(
        idToken: String,
        oAuth2ClientRegistration: OAuth2ClientRegistration,
        oAuth2AuthorizationRequest: OAuth2AuthorizationRequest
    ): OAuth2UserPrincipal {
        callCount++
        return OAuth2UserPrincipal(
            socialServiceName = socialServiceName,
            socialServiceUserId = socialServiceUserId,
            email = email
        )
    }

    fun changeSocialServiceName(socialServiceName: String) {
        this.socialServiceName = socialServiceName
    }

    fun changeSocialServiceUserId(socialServicecUserId: String) {
        this.socialServiceUserId = socialServicecUserId
    }

    fun changeEmail(email: String) {
        this.email = email
    }
}
