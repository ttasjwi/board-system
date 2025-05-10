package com.ttasjwi.board.system.user.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.util.*
import com.ttasjwi.board.system.user.domain.RegisterUserRequest
import com.ttasjwi.board.system.user.domain.RegisterUserResponse
import com.ttasjwi.board.system.user.domain.RegisterUserUseCase
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

@DisplayName("[user-web-adapter] RegisterUserController 테스트")
@WebMvcTest(RegisterUserController::class)
class RegisterUserControllerTest : UserRestDocsTest() {

    @MockkBean
    private lateinit var registerUserUseCase: RegisterUserUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/users"
        val request = RegisterUserRequest(
            email = "hello@gmail.com",
            password = "1234",
            username = "ttasjwi",
            nickname = "땃쥐"
        )

        val response = RegisterUserResponse(
            userId = "1",
            email = request.email!!,
            username = request.username!!,
            nickname = request.nickname!!,
            role = "USER",
            registeredAt = appDateTimeFixture(minute = 29).toZonedDateTime()
        )

        every { registerUserUseCase.register(request) } returns response

        // when
        // then
        mockMvc
            .perform(
                request(HttpMethod.POST, url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
            )
            .andExpectAll(
                status().isCreated,
                jsonPath("$.userId").value(response.userId),
                jsonPath("$.email").value(response.email),
                jsonPath("$.username").value(response.username),
                jsonPath("$.nickname").value(response.nickname),
                jsonPath("$.role").value(response.role),
                jsonPath("$.registeredAt").value("2025-01-01T00:29:00+09:00"),
            )
            .andDocument(
                identifier = "register-user-success",
                requestBody(
                    "email"
                            type STRING
                            means "이메일"
                            constraint "유효한 포맷이어야 하고, 인증된(현재 유효한) 이메일이어야 함. ",
                    "password"
                            type STRING
                            means "패스워드"
                            constraint "4자 이상 32자 이하의 문자열",
                    "username"
                            type STRING
                            means "사용자아이디(username)"
                            constraint "4자 이상 15자 이하, 영어 소문자, 숫자, 언더바만 허용. 공백 허용 안 됨.",
                    "nickname"
                            type STRING
                            means "닉네임"
                            constraint "1자 이상 15자 이하, 한글, 영문자(대,소), 숫자만 허용"
                ),
                responseBody(
                    "userId"
                            type STRING
                            means "사용자 식별자(Id)",
                    "email"
                            type STRING
                            means "사용자 이메일",
                    "username"
                            type STRING
                            means "사용자관점 아이디",
                    "nickname"
                            type STRING
                            means "사용자 닉네임",
                    "role"
                            type STRING
                            means "사용자 역할",
                    "registeredAt"
                            type DATETIME
                            means "회원 가입 시점"
                )
            )
        verify(exactly = 1) { registerUserUseCase.register(request) }
    }
}
