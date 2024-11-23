package com.ttasjwi.board.system.external.spring.security.exception

import com.ttasjwi.board.system.logging.getLogger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.web.servlet.HandlerExceptionResolver

class CustomAuthenticationFailureHandler(
    private val handlerExceptionResolver: HandlerExceptionResolver
) : AuthenticationFailureHandler {

    companion object {
        private val log = getLogger(CustomAuthenticationFailureHandler::class.java)
        private val INVALID_CLIENT_REGISTRATION_REGEX = Regex("Invalid Client Registration with Id: (.+)")
    }

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        val message = authException.message

        if (message != null && INVALID_CLIENT_REGISTRATION_REGEX.matches(message)) {
            val providerId = INVALID_CLIENT_REGISTRATION_REGEX.find(message)!!.groups[1]!!.value
            val ex = InvalidOAuth2ProviderIdException(providerId, authException)
            log.warn(ex)
            throw ex
        }
        handlerExceptionResolver.resolveException(request, response, null, authException)
    }
}
