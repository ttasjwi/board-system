package com.ttasjwi.board.system.core.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.savedrequest.NullRequestCache

@Configuration
class FilterChainConfig {

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
        }
        return http.build()
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
