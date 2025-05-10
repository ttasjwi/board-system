package com.ttasjwi.board.system.user.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.util.*
import com.ttasjwi.board.system.user.domain.LoginRequest
import com.ttasjwi.board.system.user.domain.LoginResponse
import com.ttasjwi.board.system.user.domain.LoginUseCase
import com.ttasjwi.board.system.user.test.base.UserRestDocsTest
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DisplayName("[user-web-adapter] LoginController 테스트")
@WebMvcTest(LoginController::class)
class LoginControllerTest : UserRestDocsTest() {

    @MockkBean
    private lateinit var loginUseCase: LoginUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/auth/login"
        val request = LoginRequest(
            email = "test@test.com",
            password = "1234567"
        )
        val currentTime = appDateTimeFixture(minute = 18)
        changeCurrentTime(currentTime)

        val response = LoginResponse(
            accessToken = "accessToken",
            accessTokenExpiresAt = currentTime.plusMinutes(30).toZonedDateTime(),
            accessTokenType = "Bearer",
            refreshToken = "refreshToken",
            refreshTokenExpiresAt = currentTime.plusHours(24).toZonedDateTime(),
        )

        every { loginUseCase.login(request) } returns response

        mockMvc
            .perform(
                request(HttpMethod.POST, url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.accessToken").value(response.accessToken),
                jsonPath("$.accessTokenExpiresAt").value("2025-01-01T00:48:00+09:00"),
                jsonPath("$.accessTokenType").value("Bearer"),
                jsonPath("$.refreshToken").value(response.refreshToken),
                jsonPath("$.refreshTokenExpiresAt").value("2025-01-02T00:18:00+09:00"),
            )
            .andDocument(
                identifier = "login-success",
                requestBody(
                    "email"
                            type STRING
                            means "이메일"
                            constraint "존재하는 회원의 이메일이어야함.",
                    "password"
                            type STRING
                            means "패스워드"
                            constraint "실제 회원의 패스워드와 일치해야함.",
                ),
                responseBody(
                    "accessToken"
                            type STRING
                            means "액세스 토큰",
                    "accessTokenExpiresAt"
                            type DATETIME
                            means "액세스 토큰 만료시각",
                    "accessTokenType"
                            type STRING
                            means "액세스 토큰 타입",
                    "refreshToken"
                            type STRING
                            means "리프레시 토큰",
                    "refreshTokenExpiresAt"
                            type DATETIME
                            means "리프레시 토큰 만료시각",
                )
            )
        verify(exactly = 1) { loginUseCase.login(request) }
    }
}
