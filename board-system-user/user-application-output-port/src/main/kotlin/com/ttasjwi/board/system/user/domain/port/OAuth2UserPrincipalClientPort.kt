package com.ttasjwi.board.system.user.domain.port

import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.model.OAuth2UserPrincipal

interface OAuth2UserPrincipalClientPort {
    fun fetch(accessToken: String, oAuth2ClientRegistration: OAuth2ClientRegistration): OAuth2UserPrincipal
}
