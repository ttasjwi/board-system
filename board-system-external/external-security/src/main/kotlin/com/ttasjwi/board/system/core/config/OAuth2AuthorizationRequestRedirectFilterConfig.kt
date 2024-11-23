package com.ttasjwi.board.system.core.config

import com.ttasjwi.board.system.external.spring.security.exception.CustomAuthenticationFailureHandler
import com.ttasjwi.board.system.external.spring.security.oauth2.CustomOAuth2AuthorizationRequestResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.web.savedrequest.NullRequestCache
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
class OAuth2AuthorizationRequestRedirectFilterConfig @Autowired constructor(
    private val clientRegistrationRepository: ClientRegistrationRepository,
    @Qualifier("handlerExceptionResolver")
    private val handlerExceptionResolver: HandlerExceptionResolver,
    private val authorizationRequestRepository: AuthorizationRequestRepository<OAuth2AuthorizationRequest>
) {

    companion object {
        private const val AUTHORIZATION_REQUEST_BASE_URI = "/api/v1/auth/oauth2/authorization"
    }

    @Bean
    fun customOauth2AuthorizationRequestRedirectFilter(): OAuth2AuthorizationRequestRedirectFilter {
        val oauth2AuthorizationRequestRedirectFilter = OAuth2AuthorizationRequestRedirectFilter(
            customOAuth2AuthorizationRequestResolver()
        )
        oauth2AuthorizationRequestRedirectFilter.setAuthorizationRequestRepository(authorizationRequestRepository)
        oauth2AuthorizationRequestRedirectFilter.setRequestCache(NullRequestCache())
        oauth2AuthorizationRequestRedirectFilter.setAuthenticationFailureHandler(
            CustomAuthenticationFailureHandler(
                handlerExceptionResolver
            )
        )
        return oauth2AuthorizationRequestRedirectFilter
    }

    private fun customOAuth2AuthorizationRequestResolver(): OAuth2AuthorizationRequestResolver {
        return CustomOAuth2AuthorizationRequestResolver.of(
            clientRegistrationRepository = clientRegistrationRepository,
            authorizationRequestBaseUri = AUTHORIZATION_REQUEST_BASE_URI
        )
    }
}
