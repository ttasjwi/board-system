package com.ttasjwi.board.system.core.config

import com.ttasjwi.board.system.auth.domain.service.AccessTokenManager
import com.ttasjwi.board.system.core.time.TimeManager
import com.ttasjwi.board.system.external.spring.security.authentication.AccessTokenAuthenticationFilter
import com.ttasjwi.board.system.external.spring.security.exception.CustomAccessDeniedHandler
import com.ttasjwi.board.system.external.spring.security.exception.CustomAuthenticationEntryPoint
import com.ttasjwi.board.system.external.spring.security.exception.CustomAuthenticationFailureHandler
import com.ttasjwi.board.system.external.spring.security.oauth2.CustomOAuth2AuthorizationRequestResolver
import com.ttasjwi.board.system.external.spring.security.support.BearerTokenResolver
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.savedrequest.NullRequestCache
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
class FilterChainConfig(
    private val accessTokenManager: AccessTokenManager,
    private val timeManager: TimeManager,
    @Qualifier(value = "handlerExceptionResolver")
    private val handlerExceptionResolver: HandlerExceptionResolver,
    private val clientRegistrationRepository: ClientRegistrationRepository,
    private val authorizationRequestRepository: AuthorizationRequestRepository<OAuth2AuthorizationRequest>
) {

    companion object {
        private const val OAUTH2_AUTHORIZATION_REQUEST_BASE_URI = "/api/v1/auth/oauth2/authorization"
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(PathRequest.toStaticResources().atCommonLocations(), permitAll)
                authorize(HttpMethod.GET, "/api/v1/deploy/health-check", permitAll)
                authorize(HttpMethod.GET, "/api/v1/members/email-available", permitAll)
                authorize(HttpMethod.GET, "/api/v1/members/username-available", permitAll)
                authorize(HttpMethod.GET, "/api/v1/members/nickname-available", permitAll)
                authorize(HttpMethod.POST, "/api/v1/members/email-verification/start", permitAll)
                authorize(HttpMethod.POST, "/api/v1/members/email-verification", permitAll)
                authorize(HttpMethod.POST, "/api/v1/members", permitAll)
                authorize(HttpMethod.POST, "/api/v1/auth/login", permitAll)
                authorize(HttpMethod.POST, "/api/v1/auth/token-refresh", permitAll)
                authorize(anyRequest, authenticated)
            }


            oauth2Login {
                authorizationEndpoint {
                    baseUri = OAUTH2_AUTHORIZATION_REQUEST_BASE_URI
                }
            }

            // OAuth2 인가요청 리다이렉트 필터
            addFilterBefore<OAuth2AuthorizationRequestRedirectFilter>(customOauth2AuthorizationRequestRedirectFilter())

            csrf { disable() }

            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }

            requestCache {
                requestCache = NullRequestCache()
            }

            exceptionHandling {
                authenticationEntryPoint = CustomAuthenticationEntryPoint(handlerExceptionResolver)
                accessDeniedHandler = CustomAccessDeniedHandler(handlerExceptionResolver)
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(accessTokenAuthenticationFilter())
        }
        return http.build()
    }

    private fun accessTokenAuthenticationFilter(): OncePerRequestFilter {
        return AccessTokenAuthenticationFilter(
            bearerTokenResolver = BearerTokenResolver(),
            accessTokenManager = accessTokenManager,
            timeManager = timeManager,
        )
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
            authorizationRequestBaseUri = OAUTH2_AUTHORIZATION_REQUEST_BASE_URI
        )
    }
}
