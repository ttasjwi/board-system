package com.ttasjwi.board.system.user.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.util.*
import com.ttasjwi.board.system.user.domain.EmailVerificationStartRequest
import com.ttasjwi.board.system.user.domain.EmailVerificationStartResponse
import com.ttasjwi.board.system.user.domain.EmailVerificationStartUseCase
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

@DisplayName("[user-web-adapter] EmailVerificationStartController 테스트")
@WebMvcTest(EmailVerificationStartController::class)
class EmailVerificationStartControllerTest : UserRestDocsTest() {

    @MockkBean
    private lateinit var emailVerificationStartUseCase: EmailVerificationStartUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/users/email-verification/start"
        val request = EmailVerificationStartRequest(email = "hello@gmail.com")
        val response = EmailVerificationStartResponse(
            email = request.email!!,
            codeExpiresAt = appDateTimeFixture(minute = 5).toZonedDateTime()
        )

        every { emailVerificationStartUseCase.startEmailVerification(request) } returns response

        mockMvc
            .perform(
                request(HttpMethod.POST, url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.email").value(response.email),
                jsonPath("$.codeExpiresAt").value("2025-01-01T00:05:00+09:00"),
            )
            .andDocument(
                identifier = "email-verification-start-success",
                requestBody(
                    "email"
                            type STRING
                            means "이메일"
                            constraint "유효한 형식의 이메일이어야함.",
                ),
                responseBody(
                    "email"
                            type STRING
                            means "요청 시 전달한 이메일",
                    "codeExpiresAt"
                            type DATETIME
                            means "이메일 인증을 위한 code 값이 만료되는 시각(유효한계시각)"
                )
            )
        verify(exactly = 1) { emailVerificationStartUseCase.startEmailVerification(request) }
    }
}
