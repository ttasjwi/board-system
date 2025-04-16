package com.ttasjwi.board.system.user.infra.spring.security.oauth2.config

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

class OAuth2SecurityDsl(
    private val customOAuth2AuthorizationRequestRedirectFilter: OAuth2AuthorizationRequestRedirectFilter,
    private val oauth2AuthorizationRequestBaseUri: String,
    private val oauth2LoginProcessingUrlPattern: String,
    private val customOAuth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User>,
    private val customOidcUserService: OidcUserService,
    private val customOAuth2AuthorizationRequestRepository: AuthorizationRequestRepository<OAuth2AuthorizationRequest>,
    private val customOAuth2AuthorizedClientRepository: OAuth2AuthorizedClientRepository,
    private val customAuthenticationSuccessHandler: AuthenticationSuccessHandler,
    private val customAuthenticationFailureHandler: AuthenticationFailureHandler,

    ) {

    fun apply(http: HttpSecurity) {
        http {
            oauth2Login {
                loginProcessingUrl = oauth2LoginProcessingUrlPattern
                authorizationEndpoint {
                    baseUri = oauth2AuthorizationRequestBaseUri
                    authorizationRequestRepository = customOAuth2AuthorizationRequestRepository
                }
                userInfoEndpoint {
                    userService = customOAuth2UserService
                    oidcUserService = customOidcUserService
                }
                authorizedClientRepository = customOAuth2AuthorizedClientRepository
                authenticationSuccessHandler = customAuthenticationSuccessHandler
                authenticationFailureHandler = customAuthenticationFailureHandler
            }

            // OAuth2 인가요청 리다이렉트 필터
            addFilterBefore<OAuth2AuthorizationRequestRedirectFilter>(customOAuth2AuthorizationRequestRedirectFilter)
        }
    }
}
