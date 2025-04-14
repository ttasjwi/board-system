package com.ttasjwi.board.system.common.infra.websupport.exception.filter

import io.mockk.*
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.web.servlet.HandlerExceptionResolver


@DisplayName("CustomExceptionHandleFilter : 뒤에서 발생하는 예외들을 HandlerExceptionResolver 에게 위임")
class CustomExceptionHandleFilterTest {

    private lateinit var customExceptionHandleFilter: CustomExceptionHandleFilter
    private lateinit var handlerExceptionResolver: HandlerExceptionResolver
    private lateinit var filterChain: FilterChain
    private lateinit var request: HttpServletRequest
    private lateinit var response: HttpServletResponse

    @BeforeEach
    fun setup() {
        request = mockk<HttpServletRequest>()
        response = mockk<HttpServletResponse>()
        handlerExceptionResolver = mockk<HandlerExceptionResolver>()
        filterChain = mockk<FilterChain>()
        customExceptionHandleFilter = CustomExceptionHandleFilter(
            handlerExceptionResolver
        )
    }

    @Test
    @DisplayName("성공 테스트")
    fun successTest() {
        // given
        every { filterChain.doFilter(request, response) } just Runs

        // when
        assertDoesNotThrow {
            customExceptionHandleFilter.doFilterInternal(request, response, filterChain)
        }

        // then
        verify(exactly = 1) { filterChain.doFilter(request, response)  }
        verify(exactly = 0) { handlerExceptionResolver.resolveException(request, response, anyNullable(), any(Exception::class)) }
    }

    @Test
    @DisplayName("다음 필터 이후에서 예외 발생시, HandlerExceptionResolver에게 예외 처리를 위임")
    fun failureTest() {
        // given
        every { filterChain.doFilter(request, response) } throws IllegalArgumentException()
        every { handlerExceptionResolver.resolveException(request, response, anyNullable(), any(Exception::class)) }  returns null

        // when
        customExceptionHandleFilter.doFilterInternal(request, response, filterChain)

        // then
        verify(exactly = 1) { filterChain.doFilter(request, response)  }
        verify(exactly = 1) { handlerExceptionResolver.resolveException(request, response, anyNullable(), any(IllegalArgumentException::class)) }
    }

}
