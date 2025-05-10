package com.ttasjwi.board.system.user.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.util.*
import com.ttasjwi.board.system.user.domain.SocialLoginRequest
import com.ttasjwi.board.system.user.domain.SocialLoginResponse
import com.ttasjwi.board.system.user.domain.SocialLoginUseCase
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

@DisplayName("[user-web-adapter] SocialLoginController 테스트")
@WebMvcTest(SocialLoginController::class)
class SocialLoginControllerTest : UserRestDocsTest() {

    @MockkBean
    private lateinit var useCase: SocialLoginUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/auth/social-login"

        val request = SocialLoginRequest(
            code = "adfadfadfadf",
            state = "adfadfadfhadhfag",
        )

        val currentTime = appDateTimeFixture(minute = 13)
        changeCurrentTime(currentTime)

        val response = SocialLoginResponse(
            accessToken = "accessToken",
            accessTokenExpiresAt = currentTime.plusMinutes(5).toZonedDateTime(),
            accessTokenType = "Bearer",
            refreshToken = "refreshToken",
            refreshTokenExpiresAt = currentTime.plusHours(24).toZonedDateTime(),
            userCreated = true,
            createdUser = SocialLoginResponse.CreatedUser(
                userId = "12345",
                email = "test@gmail.com",
                username = "randomUserName",
                nickname = "randomNickname",
                role = "USER",
                registeredAt = currentTime.toZonedDateTime(),
            )
        )

        every { useCase.socialLogin(request) } returns response


        mockMvc
            .perform(
                request(HttpMethod.POST, url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.accessToken").value(response.accessToken),
                jsonPath("$.accessTokenExpiresAt").value("2025-01-01T00:18:00+09:00"),
                jsonPath("$.accessTokenType").value(response.accessTokenType),
                jsonPath("$.refreshToken").value(response.refreshToken),
                jsonPath("$.refreshTokenExpiresAt").value("2025-01-02T00:13:00+09:00"),
                jsonPath("$.userCreated").value(true),
                jsonPath("$.createdUser.userId").value(response.createdUser!!.userId),
                jsonPath("$.createdUser.email").value(response.createdUser!!.email),
                jsonPath("$.createdUser.username").value(response.createdUser!!.username),
                jsonPath("$.createdUser.nickname").value(response.createdUser!!.nickname),
                jsonPath("$.createdUser.role").value(response.createdUser!!.role),
                jsonPath("$.createdUser.registeredAt").value("2025-01-01T00:13:00+09:00")
            )
            .andDocument(
                identifier = "social-login-success",
                requestBody(
                    "state"
                            type STRING
                            means "최초 소셜로그인 인가 요청 시 발급받은 state",
                    "code"
                            type STRING
                            means "소셜 서비스 승인 후 발급받은 code",
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
                    "userCreated"
                            type BOOLEAN
                            means "사용자 생성 여부(최초 가입시 true)",
                    "createdUser"
                            subSectionType "CreatedUser"
                            means "생성된 사용자(최초 가입시)"
                            isOptional true,
                    "createdUser.userId"
                            type STRING
                            means "사용자 식별자(Id)"
                            isOptional true,
                    "createdUser.email"
                            type STRING
                            means "사용자 이메일"
                            isOptional true,
                    "createdUser.username"
                            type STRING
                            means "사용자관점 아이디"
                            isOptional true,
                    "createdUser.nickname"
                            type STRING
                            means "사용자 닉네임"
                            isOptional true,
                    "createdUser.role"
                            type STRING
                            means "사용자 역할"
                            isOptional true,
                    "createdUser.registeredAt"
                            type DATETIME
                            means "회원 가입 시점"
                            isOptional true
                ),
            )
    }
}
