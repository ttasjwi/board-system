package com.ttasjwi.board.system.global.springsecurity.exceptionhandle

import com.ttasjwi.board.system.auth.domain.exception.UnauthenticatedException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.web.servlet.HandlerExceptionResolver

class CustomAuthenticationEntryPoint(
    private val handlerExceptionResolver: HandlerExceptionResolver
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        var ex: Exception = authException

        if (authException is InsufficientAuthenticationException) {
            ex = UnauthenticatedException(authException)
        }

        handlerExceptionResolver.resolveException(request, response, null, ex)
    }
}
