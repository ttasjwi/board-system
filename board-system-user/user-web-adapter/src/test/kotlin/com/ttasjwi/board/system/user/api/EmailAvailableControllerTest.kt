package com.ttasjwi.board.system.user.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.test.util.*
import com.ttasjwi.board.system.user.domain.EmailAvailableRequest
import com.ttasjwi.board.system.user.domain.EmailAvailableResponse
import com.ttasjwi.board.system.user.domain.EmailAvailableUseCase
import com.ttasjwi.board.system.user.test.base.UserRestDocsTest
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpMethod
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DisplayName("[user-web-adapter] EmailAvailableController 테스트")
@WebMvcTest(EmailAvailableController::class)
class EmailAvailableControllerTest : UserRestDocsTest() {

    @MockkBean
    private lateinit var emailAvailableUseCase: EmailAvailableUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/users/email-available"

        val request = EmailAvailableRequest(
            email = "hello@gmail.com"
        )

        val response = EmailAvailableResponse(
            yourEmail = request.email!!,
            isAvailable = true,
            reasonCode = "EmailAvailableCheck.Available",
            reasonMessage = "사용 가능한 이메일",
            reasonDescription = "이 이메일은 사용 가능합니다."
        )

        every { emailAvailableUseCase.checkEmailAvailable(request) } returns response

        // when
        // then
        mockMvc.perform(
            request(HttpMethod.GET, url)
                .queryParam("email", request.email)
        )
            .andExpectAll(
                status().isOk,
                jsonPath("$.yourEmail").value(response.yourEmail),
                jsonPath("$.isAvailable").value(response.isAvailable),
                jsonPath("$.reasonCode").value(response.reasonCode),
                jsonPath("$.reasonMessage").value(response.reasonMessage),
                jsonPath("$.reasonDescription").value(response.reasonDescription),
            )
            .andDocument(
                identifier = "email-available-success",
                queryParameters(
                    "email"
                            paramMeans "유효성 확인 대상이 되는 이메일"
                ),
                responseBody(
                    "yourEmail"
                            type STRING
                            means "요청 시 전달한 이메일",
                    "isAvailable"
                            type BOOLEAN
                            means "이메일 사용이 가능한지 여부(부울값)",
                    "reasonCode"
                            type STRING
                            means "이메일 사용 가능/불가능 시 그 이유를 설명하는 코드",
                    "reasonMessage"
                            type STRING
                            means "reasonCode 를 설명하는 간단한 사유 메시지",
                    "reasonDescription"
                            type STRING
                            means "reasonCode 를 설명하는 상세한 사유 메시지"
                )
            )
        verify(exactly = 1) { emailAvailableUseCase.checkEmailAvailable(request) }
    }
}
