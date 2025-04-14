package com.ttasjwi.board.system.member.infra.spring.security.oauth2.exceptionhandle

import com.ttasjwi.board.system.member.infra.spring.security.oauth2.exception.InvalidOAuth2ProviderIdException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView

@DisplayName("CustomAuthenticationFailureHandler 테스트")
class CustomAuthenticationFailureHandlerTest {

    private lateinit var authenticationFailureHandler: AuthenticationFailureHandler
    private lateinit var handlerExceptionResolver: HandlerExceptionResolver

    @BeforeEach
    fun setup() {
        handlerExceptionResolver = mockk()
        authenticationFailureHandler = CustomAuthenticationFailureHandler(
            handlerExceptionResolver = handlerExceptionResolver
        )
    }

    @Test
    @DisplayName("소셜로그인 과정에서 잘못된 ProviderId 문제로 발생한 예외는 커스텀 예외로 변환함")
    fun test1() {
        // given
        val request = mockk<HttpServletRequest>()
        val response = mockk<HttpServletResponse>()
        val providerId = "fire"
        val exception = object : AuthenticationException("Invalid Client Registration with Id: $providerId") {}
        every {
            handlerExceptionResolver.resolveException(
                request,
                response,
                null,
                any(InvalidOAuth2ProviderIdException::class)
            )
        } returns ModelAndView()

        // when
        authenticationFailureHandler.onAuthenticationFailure(request, response, exception)

        // then
        verify(exactly = 1) {
            handlerExceptionResolver.resolveException(
                request, response, null, any(
                    InvalidOAuth2ProviderIdException::class
                )
            )
        }
    }


    @Test
    @DisplayName("예외 메시지가 없을 경우 별도로 변환하지 않고 처리됨")
    fun test2() {
        // given
        val request = mockk<HttpServletRequest>()
        val response = mockk<HttpServletResponse>()
        val exception = object : AuthenticationException(null) {}

        every {
            handlerExceptionResolver.resolveException(
                request,
                response,
                null,
                exception
            )
        } returns ModelAndView()

        // when
        authenticationFailureHandler.onAuthenticationFailure(request, response, exception)

        // then
        verify(exactly = 1) { handlerExceptionResolver.resolveException(request, response, null, exception) }
    }

    @Test
    @DisplayName("예외 메시지가 있는데 ProviderId 문제가 아닐 경우, 별도로 변환하지 않고 처리됨")
    fun test3() {
        // given
        val request = mockk<HttpServletRequest>()
        val response = mockk<HttpServletResponse>()
        val exception = object : AuthenticationException("야호") {}

        every {
            handlerExceptionResolver.resolveException(
                request,
                response,
                null,
                exception
            )
        } returns ModelAndView()

        // when
        authenticationFailureHandler.onAuthenticationFailure(request, response, exception)

        // then
        verify(exactly = 1) { handlerExceptionResolver.resolveException(request, response, null, exception) }
    }
}
