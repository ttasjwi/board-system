package com.ttasjwi.board.system.user.domain.port

import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.model.OAuth2UserPrincipal

interface OidcOAuth2UserPrincipalPort {
    fun getUserPrincipal(idToken: String, oAuth2ClientRegistration: OAuth2ClientRegistration, oAuth2AuthorizationRequest: OAuth2AuthorizationRequest): OAuth2UserPrincipal
}
