package com.ttasjwi.board.system.integration.support

import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture
import com.ttasjwi.board.system.common.logger.getLogger
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class ExceptionApiTestFilter : OncePerRequestFilter() {

    companion object {
        private val log = getLogger(ExceptionApiTestFilter::class.java)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val param: String? = request.getParameter("ex")

        if (param == "true") {
            log.info{ "ex param 이 true 이므로 필터에서 예외를 발생시킵니다." }
            throw customExceptionFixture(
                code = "Error.Fixture",
                source = "filter",
                status = ErrorStatus.BAD_REQUEST,
                args = emptyList(),
                debugMessage = "필터에서 발생한 예외"
            )
        }
        filterChain.doFilter(request, response)
    }
}
