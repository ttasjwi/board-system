package com.ttasjwi.board.system.spring.security.exceptionhandle

import com.ttasjwi.board.system.spring.security.exception.InvalidOAuth2ProviderIdException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.web.servlet.HandlerExceptionResolver

class CustomAuthenticationFailureHandler(
    private val handlerExceptionResolver: HandlerExceptionResolver
) : AuthenticationFailureHandler {

    companion object {
        private val INVALID_CLIENT_REGISTRATION_ID_MESSAGE_REGEX = Regex("Invalid Client Registration with Id: (.+)")
    }

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        var ex: Exception = authException
        val message = authException.message

        if (message != null && INVALID_CLIENT_REGISTRATION_ID_MESSAGE_REGEX.matches(message)) {
            val providerId = INVALID_CLIENT_REGISTRATION_ID_MESSAGE_REGEX.find(message)!!.groups[1]!!.value
            ex = InvalidOAuth2ProviderIdException(providerId, authException)
        }
        handlerExceptionResolver.resolveException(request, response, null, ex)
    }
}
