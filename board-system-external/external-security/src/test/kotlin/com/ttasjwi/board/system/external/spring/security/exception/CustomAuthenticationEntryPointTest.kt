package com.ttasjwi.board.system.external.spring.security.exception

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView

@DisplayName("CustomAuthenticationEntryPoint 테스트")
class CustomAuthenticationEntryPointTest {

    private lateinit var authenticationEntryPoint: AuthenticationEntryPoint
    private lateinit var handlerExceptionResolver: HandlerExceptionResolver

    @BeforeEach
    fun setup() {
        handlerExceptionResolver = mockk()
        authenticationEntryPoint = CustomAuthenticationEntryPoint(handlerExceptionResolver)
    }


    @Test
    @DisplayName("내부적으로 HandlerExceptionResolver 를 호출한다.")
    fun handleAccessDeniedException() {
        // given
        val request = mockk<HttpServletRequest>()
        val response = mockk<HttpServletResponse>()
        val exception = InsufficientAuthenticationException("Full authentication is required to access this resource")

        every {
            handlerExceptionResolver.resolveException(
                request,
                response,
                null,
                exception
            )
        } returns ModelAndView()

        // when
        authenticationEntryPoint.commence(request, response, exception)

        // then
        verify(exactly = 1) { handlerExceptionResolver.resolveException(request, response, null, exception) }
    }
}
