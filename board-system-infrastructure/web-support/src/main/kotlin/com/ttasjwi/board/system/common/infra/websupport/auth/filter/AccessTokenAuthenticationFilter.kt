package com.ttasjwi.board.system.common.infra.websupport.auth.filter

import com.ttasjwi.board.system.common.auth.AccessTokenParsePort
import com.ttasjwi.board.system.common.infra.websupport.auth.token.BearerTokenResolver
import com.ttasjwi.board.system.common.time.TimeManager
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

internal class AccessTokenAuthenticationFilter(
    private val accessTokenParsePort: AccessTokenParsePort,
    private val timeManager: TimeManager,
) : OncePerRequestFilter() {

    private val bearerTokenResolver: BearerTokenResolver = BearerTokenResolver()

    public override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        // 이미 인증됐다면 통과
        if (isAuthenticated()) {
            filterChain.doFilter(request, response)
            return
        }

        // 헤더를 통해 토큰을 가져옴. 없다면 통과
        val tokenValue = bearerTokenResolver.resolve(request)
        if (tokenValue == null) {
            filterChain.doFilter(request, response)
            return
        }

        // 토큰값을 통해 인증
        val authentication = attemptAuthenticate(tokenValue)

        // 인증 결과를 SecurityContextHolder 에 저장
        saveAuthenticationToSecurityContextHolder(authentication)

        // 통과
        try {
            filterChain.doFilter(request, response)
        } finally {
            SecurityContextHolder.getContextHolderStrategy().clearContext()
        }
    }

    private fun isAuthenticated() = SecurityContextHolder.getContextHolderStrategy().context.authentication != null

    private fun attemptAuthenticate(tokenValue: String): com.ttasjwi.board.system.common.infra.websupport.auth.security.AuthUserAuthentication {
        val accessToken = accessTokenParsePort.parse(tokenValue)
        val currentTime = timeManager.now()
        accessToken.throwIfExpired(currentTime)
        return com.ttasjwi.board.system.common.infra.websupport.auth.security.AuthUserAuthentication.from(accessToken.authUser)
    }

    private fun saveAuthenticationToSecurityContextHolder(authentication: com.ttasjwi.board.system.common.infra.websupport.auth.security.AuthUserAuthentication) {
        val securityContext = SecurityContextHolder.getContextHolderStrategy().createEmptyContext()
        securityContext.authentication = authentication
        SecurityContextHolder.getContextHolderStrategy().context = securityContext
    }
}
