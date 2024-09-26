package com.ttasjwi.board.system.deploy.api

import com.ttasjwi.board.system.deploy.config.ServerProperties
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ActiveProfiles("test")
@WebMvcTest(controllers = [DeployController::class])
@EnableConfigurationProperties(ServerProperties::class)
@AutoConfigureMockMvc
@DisplayName("HealthCheckController 중형 테스트: 스프링 MVC와 컨트롤러가 잘 결합하여 동작하는가?")
class HealthCheckControllerMiddleTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var serverProperties: ServerProperties

    @Test
    @DisplayName("healthCheck : 현재 서버의 프로퍼티를 응답한다")
    fun healthCheckTest() {
        mockMvc
            .perform(get("/api/v1/deploy/health-check"))
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("\$.serverName").value(serverProperties.name),
                jsonPath("\$.serverAddress").value(serverProperties.address),
                jsonPath("\$.serverPort").value(serverProperties.port),
                jsonPath("\$.serverEnv").value(serverProperties.env),
            )
    }

    @Test
    @DisplayName("env : 현재 서버의 env 를 응답한다")
    fun envTest() {
        mockMvc
            .perform(get("/api/v1/deploy/env"))
            .andExpectAll(
                status().isOk,
                content().contentType("text/plain;charset=UTF-8"),
                content().string(serverProperties.env)
            )
    }
}
