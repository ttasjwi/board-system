package com.ttasjwi.board.system.common.infra.websupport.auth.token

import com.ttasjwi.board.system.common.infra.websupport.auth.exception.InvalidAuthorizationHeaderFormatException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpServletRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpHeaders

@DisplayName("BearerTokenResolver: Authorization 헤더의 Bearer 뒤에 위치한 토큰값을 분리한다.")
class BearerTokenResolverTest {
    private lateinit var bearerTokenResolver: BearerTokenResolver

    @BeforeEach
    fun setup() {
        bearerTokenResolver = BearerTokenResolver()
    }

    @Test
    @DisplayName("헤더값이 유효하다면, 토큰값이 성공적으로 분리된다.")
    fun testSuccess() {
        // given
        val request = mockk<HttpServletRequest>()
        every { request.getHeader(HttpHeaders.AUTHORIZATION) } returns "Bearer validToken123"


        // when
        val token = bearerTokenResolver.resolve(request)

        // then
        assertThat(token).isEqualTo("validToken123")
        verify { request.getHeader(HttpHeaders.AUTHORIZATION) }
    }

    @Test
    @DisplayName("Authorization 헤더값이 없을 경우 null 이 반환된다.")
    fun authorizationHeaderNull() {
        // given
        val request = mockk<HttpServletRequest>()
        every { request.getHeader(HttpHeaders.AUTHORIZATION) } returns null

        // when
        val token = bearerTokenResolver.resolve(request)

        // then
        assertThat(token).isNull()
    }

    @Test
    @DisplayName("Authorization 헤더값이 Bearer 로 시작하지 않으면 예외가 발생한다.")
    fun testBadAuthorizationHeader() {
        // given
        val request = mockk<HttpServletRequest>()
        every { request.getHeader(HttpHeaders.AUTHORIZATION) } returns "Basic abc123"

        // when
        // then
        assertThrows<InvalidAuthorizationHeaderFormatException> { bearerTokenResolver.resolve(request) }
        verify { request.getHeader(HttpHeaders.AUTHORIZATION) }
    }
}
