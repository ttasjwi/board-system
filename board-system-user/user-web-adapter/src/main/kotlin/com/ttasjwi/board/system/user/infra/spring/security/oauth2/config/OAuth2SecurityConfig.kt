package com.ttasjwi.board.system.user.infra.spring.security.oauth2.config

import com.ttasjwi.board.system.user.api.SocialLoginSuccessHandler
import com.ttasjwi.board.system.user.domain.SocialLoginUseCase
import com.ttasjwi.board.system.user.infra.spring.security.oauth2.CustomOAuth2AuthorizationRequestResolver
import com.ttasjwi.board.system.user.infra.spring.security.oauth2.NullOAuth2AuthorizedClientRepository
import com.ttasjwi.board.system.user.infra.spring.security.oauth2.exceptionhandle.CustomAuthenticationFailureHandler
import com.ttasjwi.board.system.user.infra.spring.security.oauth2.principal.CustomOAuth2UserService
import com.ttasjwi.board.system.user.infra.spring.security.oauth2.principal.CustomOidcUserService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.savedrequest.NullRequestCache
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
class OAuth2SecurityConfig(
    @Qualifier(value = "handlerExceptionResolver")
    private val handlerExceptionResolver: HandlerExceptionResolver,
    private val socialLoginUseCase: SocialLoginUseCase,
    private val oauth2AuthorizationRequestRepository: AuthorizationRequestRepository<OAuth2AuthorizationRequest>,
    private val clientRegistrationRepository: ClientRegistrationRepository,
) {

    companion object {
        private const val OAUTH2_AUTHORIZATION_REQUEST_BASE_URI = "/api/v1/auth/oauth2/authorization"
        private const val OAUTH2_LOGIN_PROCESSING_URL_PATTERN = "/api/v1/auth/login/oauth2/code/*"
    }

    @Bean
    fun oauth2SecurityDsl(): OAuth2SecurityDsl {
        return OAuth2SecurityDsl(
            customOAuth2AuthorizationRequestRedirectFilter = customOauth2AuthorizationRequestRedirectFilter,
            oauth2AuthorizationRequestBaseUri = OAUTH2_AUTHORIZATION_REQUEST_BASE_URI,
            oauth2LoginProcessingUrlPattern = OAUTH2_LOGIN_PROCESSING_URL_PATTERN,
            customOAuth2UserService = customOAuth2UserService,
            customOidcUserService = customOidcUserService,
            customOAuth2AuthorizationRequestRepository = oauth2AuthorizationRequestRepository,
            customOAuth2AuthorizedClientRepository = NullOAuth2AuthorizedClientRepository(),
            customAuthenticationSuccessHandler = SocialLoginSuccessHandler(socialLoginUseCase),
            customAuthenticationFailureHandler = customAuthenticationFailureHandler,
        )
    }

    private val customOauth2AuthorizationRequestRedirectFilter: OAuth2AuthorizationRequestRedirectFilter by lazy {
        val oauth2AuthorizationRequestRedirectFilter =
            OAuth2AuthorizationRequestRedirectFilter(customOAuth2AuthorizationRequestResolver)
        oauth2AuthorizationRequestRedirectFilter.setAuthorizationRequestRepository(oauth2AuthorizationRequestRepository)
        oauth2AuthorizationRequestRedirectFilter.setRequestCache(NullRequestCache())
        oauth2AuthorizationRequestRedirectFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler)

        oauth2AuthorizationRequestRedirectFilter
    }

    private val customOAuth2AuthorizationRequestResolver: OAuth2AuthorizationRequestResolver by lazy {
        CustomOAuth2AuthorizationRequestResolver.of(
            clientRegistrationRepository = clientRegistrationRepository,
            authorizationRequestBaseUri = OAUTH2_AUTHORIZATION_REQUEST_BASE_URI
        )
    }

    private val customOAuth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User> by lazy {
        CustomOAuth2UserService(DefaultOAuth2UserService())
    }

    private val customOidcUserService: OidcUserService by lazy {
        CustomOidcUserService(OidcUserService())
    }

    private val customAuthenticationFailureHandler: AuthenticationFailureHandler by lazy {
        CustomAuthenticationFailureHandler(handlerExceptionResolver)
    }
}
