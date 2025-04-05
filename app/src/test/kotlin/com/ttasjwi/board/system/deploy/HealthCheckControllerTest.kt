package com.ttasjwi.board.system.deploy

import com.ttasjwi.board.system.IntegrationTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@DisplayName("HealthCheckController 중형 테스트: 스프링 MVC와 컨트롤러가 잘 결합하여 동작하는가?")
class HealthCheckControllerTest : IntegrationTest() {

    @Test
    @DisplayName("healthCheck : 현재 서버가 살아있다면 200 상태코드와 함께 프로필 관련 정보를 포함하여 json 으로 응답한다.")
    fun healthCheckTest() {
        mockMvc
            .perform(get("/api/v1/deploy/health-check"))
            .andDo(print())
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.profile").value("test")
            )
    }
}
