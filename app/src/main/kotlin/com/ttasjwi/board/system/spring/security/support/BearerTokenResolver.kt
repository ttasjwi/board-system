package com.ttasjwi.board.system.spring.security.support

import com.ttasjwi.board.system.spring.security.exception.InvalidAuthorizationHeaderFormatException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders

class BearerTokenResolver {

    private val bearerTokenHeaderName = HttpHeaders.AUTHORIZATION

    fun resolve(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader(this.bearerTokenHeaderName) ?: return null

        if (!authorizationHeader.startsWith("Bearer ", ignoreCase = true)) {
            throw InvalidAuthorizationHeaderFormatException()
        }
        return authorizationHeader.substring(7)
    }

}
