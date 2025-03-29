package com.ttasjwi.board.system.deploy.api

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [HealthCheckController::class])
@AutoConfigureMockMvc
@DisplayName("HealthCheckController 중형 테스트: 스프링 MVC와 컨트롤러가 잘 결합하여 동작하는가?")
class HealthCheckControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("healthCheck : 현재 서버가 살아있다면 200 상태코드와 함께 프로필 관련 정보를 포함하여 json 으로 응답한다.")
    fun healthCheckTest() {
        mockMvc
            .perform(get("/api/v1/deploy/health-check"))
            .andDo(print())
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.isSuccess").value(true),
                jsonPath("$.code").value("HealthCheck.Success"),
                jsonPath("$.message").value("헬스체크 성공"),
                jsonPath("$.description").value("헬스체크에 성공했습니다. 현재 서버 프로필은 '\$.data.profile'필드를 참고하세요."),
                jsonPath("$.data.profile").value("test")
            )
    }
}
