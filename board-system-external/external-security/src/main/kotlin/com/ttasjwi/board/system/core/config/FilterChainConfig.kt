package com.ttasjwi.board.system.core.config

import com.ttasjwi.board.system.external.spring.security.authentication.AccessTokenAuthenticationFilter
import com.ttasjwi.board.system.external.spring.security.support.BearerTokenResolver
import com.ttasjwi.board.system.auth.domain.service.AccessTokenManager
import com.ttasjwi.board.system.core.time.TimeManager
import com.ttasjwi.board.system.external.spring.security.exception.CustomAccessDeniedHandler
import com.ttasjwi.board.system.external.spring.security.exception.CustomAuthenticationEntryPoint
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
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
) {

    @Bean
    @Order(0)
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            securityMatcher("/api/**")
            authorizeHttpRequests {
                authorize(HttpMethod.GET, "/api/v1/deploy/health-check", permitAll)

                authorize(HttpMethod.GET, "/api/v1/members/email-available", permitAll)
                authorize(HttpMethod.GET, "/api/v1/members/username-available", permitAll)
                authorize(HttpMethod.GET, "/api/v1/members/nickname-available", permitAll)

                authorize(HttpMethod.POST, "/api/v1/members/email-verification/start", permitAll)
                authorize(HttpMethod.POST, "/api/v1/members/email-verification", permitAll)
                authorize(HttpMethod.POST, "/api/v1/members", permitAll)

                authorize(HttpMethod.POST, "/api/v1/auth/login", permitAll)

                authorize(anyRequest, authenticated)
            }

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
    @Order(1)
    fun staticResourceSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }
        }
        return http.build()
    }
}
