package com.ttasjwi.board.system.global.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.LocaleResolver

class CustomLocaleContextFilter(
    private val localeResolver: LocaleResolver
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val locale = localeResolver.resolveLocale(request)
        LocaleContextHolder.setLocale(locale)

        try {
            filterChain.doFilter(request, response)
        } finally {
            LocaleContextHolder.resetLocaleContext()
        }
    }
}
