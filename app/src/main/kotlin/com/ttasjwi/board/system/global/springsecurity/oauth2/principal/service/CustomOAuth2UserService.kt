package com.ttasjwi.board.system.global.springsecurity.oauth2.principal.service

import com.ttasjwi.board.system.global.springsecurity.oauth2.principal.model.CustomOAuth2User
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOAuth2UserService(
    private val delegate: OAuth2UserService<OAuth2UserRequest, OAuth2User>
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val clientRegistration = userRequest.clientRegistration
        val oauth2User = delegate.loadUser(userRequest)

        return CustomOAuth2User.from(
            socialServiceName = clientRegistration.registrationId,
            oauth2User = oauth2User
        )
    }
}
