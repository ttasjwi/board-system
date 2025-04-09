package com.ttasjwi.board.system.common.websupport.test.mvc

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.common.websupport.test.WebSupportIntegrationTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@DisplayName("WebMvc에서 예외 처리가 잘 적용되는 지 테스트")
class ExceptionHandleMvcTest : WebSupportIntegrationTest() {

    @Test
    @DisplayName("'/no-ex' 엔드포인트를 호출하면 hello 문자열이 반환된다.")
    fun caseTestSuccessEndPointSuccess() {
        mockMvc
            .perform(
                get("/api/v1/test/web-supports/exceptions/no-ex")
            )
            .andDo(print())
            .andExpectAll(
                status().isOk,
                content().contentType("text/plain;charset=UTF-8"),
                content().string("hello")
            )
    }

    @Test
    @DisplayName("'/no-ex' 엔드포인트 호출시 ex=true 파라미터를 전달하면 필터에서 예외가 발생하고, 이를 앞단의 필터가 처리한다.")
    fun caseFilterException() {
        mockMvc
            .perform(
                get("/api/v1/test/web-supports/exceptions/no-ex?ex=true") // ex=true 파라미터를 전달하면 ExceptionApiTestFilter에서 예외 발생함
            )
            .andDo(print())
            .andExpectAll(
                status().isBadRequest,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.errors").isNotEmpty,
                jsonPath("$.errors[0].code").value("Error.ExceptionHandleTestFilter"),
                jsonPath("$.errors[0].message").value("Error.ExceptionHandleTestFilter.message"),
                jsonPath("$.errors[0].description").value("Error.ExceptionHandleTestFilter.description"),
                jsonPath("$.errors[0].source").value("ExceptionHandleTestFilter"),
            )
    }

    @Test
    @DisplayName("컨트롤러('/throw-ex')에서 예외 발생하면 HandlerExceptionResolver 에서 처리한다.")
    fun caseControllerException() {
        mockMvc
            .perform(
                get("/api/v1/test/web-supports/exceptions/throw-ex") // 컨트롤러에서 예외 발생함
            )
            .andDo(print())
            .andExpectAll(
                status().isBadRequest,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.errors").isNotEmpty,
                jsonPath("$.errors[0].code").value("Error.ExceptionHandleTestController"),
                jsonPath("$.errors[0].message").value("Error.ExceptionHandleTestController.message"),
                jsonPath("$.errors[0].description").value("Error.ExceptionHandleTestController.description"),
                jsonPath("$.errors[0].source").value("ExceptionHandleTestController"),
            )
    }
}
