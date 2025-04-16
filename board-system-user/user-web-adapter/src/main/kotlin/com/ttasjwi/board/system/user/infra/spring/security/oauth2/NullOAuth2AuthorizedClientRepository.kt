package com.ttasjwi.board.system.user.infra.spring.security.oauth2

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository

class NullOAuth2AuthorizedClientRepository : OAuth2AuthorizedClientRepository {

    override fun <T : OAuth2AuthorizedClient?> loadAuthorizedClient(
        clientRegistrationId: String?,
        principal: Authentication?,
        request: HttpServletRequest?
    ): T? {
        return null
    }

    override fun saveAuthorizedClient(
        authorizedClient: OAuth2AuthorizedClient?,
        principal: Authentication?,
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ) {}

    override fun removeAuthorizedClient(
        clientRegistrationId: String?,
        principal: Authentication?,
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ) {}
}
