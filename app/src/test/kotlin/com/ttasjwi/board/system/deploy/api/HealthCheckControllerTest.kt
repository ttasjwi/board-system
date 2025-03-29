package com.ttasjwi.board.system.deploy.api

import com.ttasjwi.board.system.common.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.common.message.MessageResolver
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest(controllers = [HealthCheckController::class])
@AutoConfigureMockMvc
@Import(HealthCheckControllerTest.FixtureBeanConfig::class)
@DisplayName("HealthCheckController 중형 테스트: 스프링 MVC와 컨트롤러가 잘 결합하여 동작하는가?")
class HealthCheckControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @TestConfiguration
    class FixtureBeanConfig {

        @Bean
        fun messageResolverFixture(): MessageResolver {
            return MessageResolverFixture()
        }

        @Bean
        fun localeManagerFixture(): LocaleManagerFixture {
            return LocaleManagerFixture()
        }
    }

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
                jsonPath("$.message").value("HealthCheck.Success.message(locale=${Locale.KOREAN},args=[])"),
                jsonPath("$.description").value("HealthCheck.Success.description(locale=${Locale.KOREAN},args=[\$.data.profile])"),
                jsonPath("$.data.profile").value("test")
            )
    }
}
