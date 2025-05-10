package com.ttasjwi.board.system.user.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.util.*
import com.ttasjwi.board.system.user.domain.TokenRefreshRequest
import com.ttasjwi.board.system.user.domain.TokenRefreshResponse
import com.ttasjwi.board.system.user.domain.TokenRefreshUseCase
import com.ttasjwi.board.system.user.test.base.UserRestDocsTest
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DisplayName("[user-web-adapter] TokenRefreshController 테스트")
@WebMvcTest(TokenRefreshController::class)
class TokenRefreshControllerTest : UserRestDocsTest() {

    @MockkBean
    private lateinit var tokenRefreshUseCase: TokenRefreshUseCase


    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/auth/token-refresh"

        val request = TokenRefreshRequest("refreshToken")
        val response = TokenRefreshResponse(
            accessToken = "newAccessToken",
            accessTokenType = "Bearer",
            accessTokenExpiresAt = appDateTimeFixture(minute = 37).toZonedDateTime(),
            refreshToken = request.refreshToken!!,
            refreshTokenExpiresAt = appDateTimeFixture(dayOfMonth = 2, minute = 32).toZonedDateTime(),
            refreshTokenRefreshed = false,
        )

        every { tokenRefreshUseCase.tokenRefresh(request) } returns response

        // when
        // then
        mockMvc
            .perform(
                request(HttpMethod.POST, url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.accessToken").value(response.accessToken),
                jsonPath("$.accessTokenExpiresAt").value("2025-01-01T00:37:00+09:00"),
                jsonPath("$.accessTokenType").value(response.accessTokenType),
                jsonPath("$.refreshToken").value(response.refreshToken),
                jsonPath("$.refreshTokenExpiresAt").value("2025-01-02T00:32:00+09:00"),
                jsonPath("$.refreshTokenRefreshed").value(response.refreshTokenRefreshed),
            )
            .andDocument(
                identifier = "token-refresh-success",
                requestBody(
                    "refreshToken"
                            type STRING
                            means "현재 유효한 리프레시토큰 값"
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
                    "refreshTokenRefreshed"
                            type BOOLEAN
                            means "리프레시 토큰이 재갱신됐는 지 여부"
                )
            )
    }
}
