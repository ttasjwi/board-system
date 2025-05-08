package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.model.OAuth2UserPrincipal
import com.ttasjwi.board.system.user.domain.port.OAuth2UserPrincipalClientPort

class OAuth2UserPrincipalClientPortFixture(
    private var socialServiceName: String = "socialServiceName",
    private var socialServiceUserId: String = "DefaultSocialServiceUserId",
    private var email: String = "test@oidc.com"
) : OAuth2UserPrincipalClientPort {

    var callCount = 0

    override fun fetch(accessToken: String, oAuth2ClientRegistration: OAuth2ClientRegistration): OAuth2UserPrincipal {
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
