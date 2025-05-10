package com.ttasjwi.board.system.user.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.test.util.*
import com.ttasjwi.board.system.user.domain.*
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

@DisplayName("[user-web-adapter] UsernameAvailableController 테스트")
@WebMvcTest(UsernameAvailableController::class)
class UsernameAvailableControllerTest : UserRestDocsTest() {

    @MockkBean
    private lateinit var usernameAvailableUseCase: UsernameAvailableUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/users/username-available"

        val request = UsernameAvailableRequest(
            username = "ttasjwi"
        )

        val response = UsernameAvailableResponse(
            yourUsername = request.username!!,
            isAvailable = true,
            reasonCode = "UsernameAvailableCheck.Available",
            reasonMessage = "사용 가능한 사용자아이디(username)",
            reasonDescription = "이 사용자아이디(username)는 사용 가능합니다."
        )

        every { usernameAvailableUseCase.checkUsernameAvailable(request) } returns response

        // when
        // then
        mockMvc.perform(
            request(HttpMethod.GET, url)
                .queryParam("username", request.username)
        )
            .andExpectAll(
                status().isOk,
                jsonPath("$.yourUsername").value(response.yourUsername),
                jsonPath("$.isAvailable").value(response.isAvailable),
                jsonPath("$.reasonCode").value(response.reasonCode),
                jsonPath("$.reasonMessage").value(response.reasonMessage),
                jsonPath("$.reasonDescription").value(response.reasonDescription),
            )
            .andDocument(
                identifier = "username-available-success",
                queryParameters(
                    "username"
                            paramMeans "유효성 확인 대상이 되는 사용자관점 아이디(username)"
                ),
                responseBody(
                    "yourUsername"
                            type STRING
                            means "요청 시 전달한 사용자관점 아이디(username)",
                    "isAvailable"
                            type BOOLEAN
                            means "username 사용이 가능한지 여부(부울값)",
                    "reasonCode"
                            type STRING
                            means "username 사용 가능/불가능 시 그 이유를 설명하는 코드",
                    "reasonMessage"
                            type STRING
                            means "reasonCode 를 설명하는 간단한 사유 메시지",
                    "reasonDescription"
                            type STRING
                            means "reasonCode 를 설명하는 상세한 사유 메시지"
                )
            )
        verify(exactly = 1) { usernameAvailableUseCase.checkUsernameAvailable(request) }
    }
}
