package com.ttasjwi.board.system.member.infra.spring.security.oauth2.principal

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser

class CustomOidcUserService(
    private val delegate: OidcUserService
) : OidcUserService() {

    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val clientRegistration = userRequest.clientRegistration
        val oidcUser = delegate.loadUser(userRequest)

        return CustomOidcUser.from(
            socialServiceName = clientRegistration.registrationId,
            oidcUser = oidcUser
        )
    }
}
