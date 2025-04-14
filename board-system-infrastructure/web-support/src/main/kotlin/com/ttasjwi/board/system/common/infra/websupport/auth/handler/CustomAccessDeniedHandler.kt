package com.ttasjwi.board.system.common.infra.websupport.auth.handler

import com.ttasjwi.board.system.common.infra.websupport.auth.exception.AccessDeniedException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.web.servlet.HandlerExceptionResolver

class CustomAccessDeniedHandler(
    private val handlerExceptionResolver: HandlerExceptionResolver
) : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: org.springframework.security.access.AccessDeniedException
    ) {
        val customAccessDeniedException = AccessDeniedException(accessDeniedException)
        handlerExceptionResolver.resolveException(request, response, null, customAccessDeniedException)
    }
}
