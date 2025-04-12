package com.ttasjwi.board.system.common.websupport.auth.config

import com.ttasjwi.board.system.common.auth.AccessTokenParsePort
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.common.websupport.auth.filter.AccessTokenAuthenticationFilter
import com.ttasjwi.board.system.common.websupport.auth.handler.CustomAccessDeniedHandler
import com.ttasjwi.board.system.common.websupport.auth.handler.CustomAuthenticationEntryPoint
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.savedrequest.NullRequestCache
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

class CoreSecurityDsl(
    private val accessTokenParsePort: AccessTokenParsePort,
    private val handlerExceptionResolver: HandlerExceptionResolver,
    private val timeManager: TimeManager
) {

    fun apply(http: HttpSecurity) {
        http {
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
            addFilterBefore<UsernamePasswordAuthenticationFilter>(
                accessTokenAuthenticationFilter()
            )
        }
    }

    private fun accessTokenAuthenticationFilter(): OncePerRequestFilter {
        return AccessTokenAuthenticationFilter(
            accessTokenParsePort = accessTokenParsePort,
            timeManager = timeManager,
        )
    }
}
