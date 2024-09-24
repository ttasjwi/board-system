package com.ttasjwi.board.system.deploy.api

import com.ttasjwi.board.system.deploy.config.ServerProperties
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
@WebMvcTest(controllers = [HealthCheckController::class])
@EnableConfigurationProperties(ServerProperties::class)
@AutoConfigureMockMvc
class HealthCheckControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var serverProperties: ServerProperties

    @Test
    fun healthCheckTest() {
        mockMvc
            .perform(get("/api/v1/deploy/health-check"))
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("\$.serverEnv").value(serverProperties.env),
                jsonPath("\$.serverPort").value(serverProperties.port),
                jsonPath("\$.serverAddress").value(serverProperties.address),
                jsonPath("\$.serverName").value(serverProperties.name),
            )
    }

    @Test
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
