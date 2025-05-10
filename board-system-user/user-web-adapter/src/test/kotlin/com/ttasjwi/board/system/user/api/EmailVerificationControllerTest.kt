package com.ttasjwi.board.system.user.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.util.*
import com.ttasjwi.board.system.user.domain.EmailVerificationRequest
import com.ttasjwi.board.system.user.domain.EmailVerificationResponse
import com.ttasjwi.board.system.user.domain.EmailVerificationUseCase
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

@DisplayName("[user-web-adapter] EmailVerificationController 테스트")
@WebMvcTest(EmailVerificationController::class)
class EmailVerificationControllerTest : UserRestDocsTest() {

    @MockkBean
    private lateinit var emailVerificationUseCase: EmailVerificationUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/users/email-verification"
        val request = EmailVerificationRequest(email = "hello@gmail.com", code = "1234")
        val response = EmailVerificationResponse(
            email = request.email!!,
            verificationExpiresAt = appDateTimeFixture(minute = 34).toZonedDateTime()
        )
        every { emailVerificationUseCase.emailVerification(request) } returns response

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
                jsonPath("$.email").value(response.email),
                jsonPath("$.verificationExpiresAt").value("2025-01-01T00:34:00+09:00")
            )
            .andDocument(
                identifier = "email-verification-success",
                requestBody(
                    "email"
                            type STRING
                            means "인증의 대상이 되는 email",
                    "code"
                            type STRING
                            means "이메일을 통해 전달받은 이메일 인증코드"
                ),
                responseBody(
                    "email"
                            type STRING
                            means "요청 시 전달한 이메일",
                    "verificationExpiresAt"
                            type DATETIME
                            means "이메일 인증이 만료되는 시각"
                )
            )
        verify(exactly = 1) { emailVerificationUseCase.emailVerification(request) }
    }
}
