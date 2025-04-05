package com.ttasjwi.board.system.integration

import com.ttasjwi.board.system.IntegrationTest
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@DisplayName("WebMvc에서 예외 처리가 잘 적용되는 지 테스트")
class ExceptionHandleIntegrationTest : IntegrationTest() {

    @Test
    @DisplayName("'/api/v1/test/no-ex' 엔드포인트를 호출하면 hello 문자열이 반환된다.")
    fun caseTestSuccessEndPointSuccess() {
        timeManagerFixture.changeCurrentTime(appDateTimeFixture())
        mockMvc
            .perform(
                get("/api/v1/test/no-ex")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer ${generateAccessTokenValue()}")
            )
            .andDo(print())
            .andExpectAll(
                status().isOk,
                content().contentType("text/plain;charset=UTF-8"),
                content().string("hello")
            )
    }

    @Test
    @DisplayName("'/api/v1/test/no-ex' 엔드포인트 호출시 ex=true 파라미터를 전달하면 필터에서 예외가 발생하고, 이를 앞단의 필터가 처리한다.")
    fun caseFilterException() {
        timeManagerFixture.changeCurrentTime(appDateTimeFixture())

        mockMvc
            .perform(
                get("/api/v1/test/no-ex?ex=true") // ex=true 파라미터를 전달하면 ExceptionApiTestFilter에서 예외 발생함
                    .header(HttpHeaders.AUTHORIZATION, "Bearer ${generateAccessTokenValue()}")
            )
            .andDo(print())
            .andExpectAll(
                status().isBadRequest,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.errors").isNotEmpty,
                jsonPath("$.errors[0].code").value("Error.Fixture"),
                jsonPath("$.errors[0].message").value("Error.Fixture.message"),
                jsonPath("$.errors[0].description").value("Error.Fixture.description"),
                jsonPath("$.errors[0].source").value("filter"),
            )
    }

    @Test
    @DisplayName("컨트롤러에서 예외 발생하면 HandlerExceptionResolver 에서 처리한다.")
    fun caseControllerException() {
        timeManagerFixture.changeCurrentTime(appDateTimeFixture())

        mockMvc
            .perform(
                get("/api/v1/test/throw-ex") // 컨트롤러에서 예외 발생함
                    .header(HttpHeaders.AUTHORIZATION, "Bearer ${generateAccessTokenValue()}")
            )
            .andDo(print())
            .andExpectAll(
                status().isBadRequest,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.errors").isNotEmpty,
                jsonPath("$.errors[0].code").value("Error.Fixture"),
                jsonPath("$.errors[0].message").value("Error.Fixture.message"),
                jsonPath("$.errors[0].description").value("Error.Fixture.description"),
                jsonPath("$.errors[0].source").value("controller"),
            )
    }

}
