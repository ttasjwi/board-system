package com.ttasjwi.board.system.spring.security.exceptionhandle

import com.ttasjwi.board.system.domain.auth.exception.AccessDeniedException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView

@DisplayName("CustomAccessDeniedHandler 테스트")
class CustomAccessDeniedHandlerTest {

    private lateinit var accessDeniedHandler: AccessDeniedHandler
    private lateinit var handlerExceptionResolver: HandlerExceptionResolver

    @BeforeEach
    fun setup() {
        handlerExceptionResolver = mockk()
        accessDeniedHandler = CustomAccessDeniedHandler(handlerExceptionResolver)
    }

    @Test
    @DisplayName("내부적으로 HandlerExceptionResolver 를 호출한다.")
    fun handleAccessDeniedException() {
        // given
        val request = mockk<HttpServletRequest>()
        val response = mockk<HttpServletResponse>()
        val exception = org.springframework.security.access.AccessDeniedException("권한 부족")
        
        every { handlerExceptionResolver.resolveException(
            request,
            response,
            null,
            any(com.ttasjwi.board.system.domain.auth.exception.AccessDeniedException::class)
        ) } returns ModelAndView()
        
        // when
        accessDeniedHandler.handle(request, response, exception)
        
        // then
        verify(exactly = 1) { handlerExceptionResolver.resolveException(request, response, null, any(com.ttasjwi.board.system.domain.auth.exception.AccessDeniedException::class))  }
    }
}
