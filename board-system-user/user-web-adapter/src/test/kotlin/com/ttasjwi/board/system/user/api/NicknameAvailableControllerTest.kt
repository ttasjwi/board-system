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

@DisplayName("[user-web-adapter] NicknameAvailableController 테스트")
@WebMvcTest(NicknameAvailableController::class)
class NicknameAvailableControllerTest : UserRestDocsTest() {

    @MockkBean
    private lateinit var nicknameAvailableUseCase: NicknameAvailableUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/users/nickname-available"

        val request = NicknameAvailableRequest(
            nickname = "땃쥐"
        )

        val response = NicknameAvailableResponse(
            yourNickname = request.nickname!!,
            isAvailable = true,
            reasonCode = "NicknameAvailableCheck.Available",
            reasonMessage = "사용 가능한 닉네임",
            reasonDescription = "이 닉네임은 사용 가능합니다."
        )

        every { nicknameAvailableUseCase.checkNicknameAvailable(request) } returns response

        // when
        // then
        mockMvc.perform(
            request(HttpMethod.GET, url)
                .queryParam("nickname", request.nickname)
        )
            .andExpectAll(
                status().isOk,
                jsonPath("$.yourNickname").value(response.yourNickname),
                jsonPath("$.isAvailable").value(response.isAvailable),
                jsonPath("$.reasonCode").value(response.reasonCode),
                jsonPath("$.reasonMessage").value(response.reasonMessage),
                jsonPath("$.reasonDescription").value(response.reasonDescription),
            )
            .andDocument(
                identifier = "nickname-available-success",
                queryParameters(
                    "nickname"
                            paramMeans "유효성 확인 대상이 되는 닉네임"
                ),
                responseBody(
                    "yourNickname"
                            type STRING
                            means "요청 시 전달한 닉네임",
                    "isAvailable"
                            type BOOLEAN
                            means "닉네임 사용이 가능한지 여부(부울값)",
                    "reasonCode"
                            type STRING
                            means "닉네임 사용 가능/불가능 시 그 이유를 설명하는 코드",
                    "reasonMessage"
                            type STRING
                            means "reasonCode 를 설명하는 간단한 사유 메시지",
                    "reasonDescription"
                            type STRING
                            means "reasonCode 를 설명하는 상세한 사유 메시지"
                )
            )
        verify(exactly = 1) { nicknameAvailableUseCase.checkNicknameAvailable(request) }
    }
}
