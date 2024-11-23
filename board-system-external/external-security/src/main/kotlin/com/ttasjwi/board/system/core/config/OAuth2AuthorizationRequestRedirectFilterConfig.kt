package com.ttasjwi.board.system.core.config

import com.ttasjwi.board.system.external.spring.security.exception.CustomAuthenticationFailureHandler
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter
import org.springframework.security.web.savedrequest.NullRequestCache
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
class OAuth2AuthorizationRequestRedirectFilterConfig {

    companion object {
        private const val AUTHORIZATION_REQUEST_BASE_URI = "/api/v1/auth/oauth2/authorization"
    }

    @Bean
    fun customOauth2AuthorizationRequestRedirectFilter(
        clientRegistrationRepository: ClientRegistrationRepository,
        @Qualifier("handlerExceptionResolver")
        handlerExceptionResolver: HandlerExceptionResolver,
    ): OAuth2AuthorizationRequestRedirectFilter {
        val oauth2AuthorizationRequestRedirectFilter = OAuth2AuthorizationRequestRedirectFilter(
            clientRegistrationRepository,
            AUTHORIZATION_REQUEST_BASE_URI
        )
        oauth2AuthorizationRequestRedirectFilter.setRequestCache(NullRequestCache())
        oauth2AuthorizationRequestRedirectFilter.setAuthenticationFailureHandler(
            CustomAuthenticationFailureHandler(
                handlerExceptionResolver
            )
        )
        return oauth2AuthorizationRequestRedirectFilter
    }
}
