package com.ttasjwi.board.system.core.exception

import com.ttasjwi.board.system.ExceptionApiTestController
import com.ttasjwi.board.system.TestConfig
import com.ttasjwi.board.system.core.config.ExceptionHandleFilterConfig
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@ActiveProfiles("test")
@WebMvcTest(controllers = [ExceptionApiTestController::class])
@AutoConfigureMockMvc
@Import(TestConfig::class, ExceptionHandleFilterConfig::class)
@DisplayName("WebMvc에서 예외 처리가 잘 적용되는 지 테스트")
class WebMvcExceptionHandleTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("'/api/test-success' 엔드포인트를 호출하면 hello 문자열이 반환된다.")
    fun caseTestSuccessEndPointSuccess() {
        mockMvc
            .perform(get("/api/test-success"))
            .andDo(print())
            .andExpectAll(
                status().isOk,
                content().contentType("text/plain;charset=UTF-8"),
                content().string("hello")
            )
    }

    @Test
    @DisplayName("'/api/test-success' 엔드포인트 호출시 ex=true 파라미터를 전달하면 필터에서 예외가 발생하고, 이를 앞단의 필터가 처리한다.")
    fun caseFilterException() {
        mockMvc
            .perform(get("/api/test-success?ex=true")) // ex=true 파라미터를 전달하면 ExceptionApiTestFilter에서 예외 발생함
            .andDo(print())
            .andExpectAll(
                status().isBadRequest,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.isSuccess").value(false),
                jsonPath("$.code").value("Error.Occurred"),
                jsonPath("$.message").value("Error.Occurred.message(locale=${Locale.KOREAN},args=[])"),
                jsonPath("$.description").value("Error.Occurred.description(locale=${Locale.KOREAN},args=[])"),
                jsonPath("$.data").doesNotExist(),
                jsonPath("$.errors[0].code").value("Error.Fixture"),
                jsonPath("$.errors[0].message").value("Error.Fixture.message(locale=${Locale.KOREAN},args=[])"),
                jsonPath("$.errors[0].description").value("Error.Fixture.description(locale=${Locale.KOREAN},args=[])"),
                jsonPath("$.errors[0].source").value("filter"),
            )
    }

    @Test
    @DisplayName("컨트롤러에서 예외 발생하면 HandlerExceptionResolver 에서 처리한다.")
    fun caseControllerException() {
        mockMvc
            .perform(get("/api/test-ex")) // 컨트롤러에서 예외 발생함
            .andDo(print())
            .andExpectAll(
                status().isBadRequest,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.isSuccess").value(false),
                jsonPath("$.code").value("Error.Occurred"),
                jsonPath("$.message").value("Error.Occurred.message(locale=${Locale.KOREAN},args=[])"),
                jsonPath("$.description").value("Error.Occurred.description(locale=${Locale.KOREAN},args=[])"),
                jsonPath("$.data").doesNotExist(),
                jsonPath("$.errors[0].code").value("Error.Fixture"),
                jsonPath("$.errors[0].message").value("Error.Fixture.message(locale=${Locale.KOREAN},args=[])"),
                jsonPath("$.errors[0].description").value("Error.Fixture.description(locale=${Locale.KOREAN},args=[])"),
                jsonPath("$.errors[0].source").value("controller"),
            )
    }
}
