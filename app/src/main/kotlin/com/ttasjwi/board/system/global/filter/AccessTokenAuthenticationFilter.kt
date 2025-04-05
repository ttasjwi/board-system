package com.ttasjwi.board.system.global.filter

import com.ttasjwi.board.system.auth.domain.service.AccessTokenManager
import com.ttasjwi.board.system.global.springsecurity.authentication.AuthMemberAuthentication
import com.ttasjwi.board.system.global.springsecurity.token.BearerTokenResolver
import com.ttasjwi.board.system.global.time.TimeManager
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

/**
 * 스프링 시큐리티 필터체인에 위치합니다.
 */
class AccessTokenAuthenticationFilter(
    private val bearerTokenResolver: BearerTokenResolver,
    private val accessTokenManager: AccessTokenManager,
    private val timeManager: TimeManager,
) : OncePerRequestFilter() {

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

    private fun attemptAuthenticate(tokenValue: String): AuthMemberAuthentication {
        val accessToken = accessTokenManager.parse(tokenValue)
        val currentTime = timeManager.now()

        accessTokenManager.checkCurrentlyValid(accessToken, currentTime)

        return AuthMemberAuthentication.from(accessToken.authMember)
    }

    private fun saveAuthenticationToSecurityContextHolder(authentication: AuthMemberAuthentication) {
        val securityContext = SecurityContextHolder.getContextHolderStrategy().createEmptyContext()
        securityContext.authentication = authentication
        SecurityContextHolder.getContextHolderStrategy().context = securityContext
    }
}
