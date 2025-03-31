package com.ttasjwi.board.system.integration

import com.ttasjwi.board.system.IntegrationTest
import com.ttasjwi.board.system.common.time.fixture.timeFixture
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@DisplayName("WebMvc에서 메시지/국제화가 잘 적용되는 지 테스트")
class MessageIntegrationTest : IntegrationTest() {

    @Test
    @DisplayName("Accept Language 헤더가 없을 때 디폴트 로케일인 한국어 메시지 설정을 따른다.")
    fun test1() {
        timeManagerFixture.changeCurrentTime(timeFixture())
        mockMvc
            .perform(
                get("/api/v1/test/message")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer ${generateAccessTokenValue()}")
            )
            .andDo(print())
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.locale").value(Locale.KOREAN.toString()),
                jsonPath("$.code").value("Example"),
                jsonPath("$.message").value("예제 메시지"),
                jsonPath("$.description").value("예제 설명(args=1,2,3)"),
            )
    }

    @Test
    @DisplayName("Accept Language 헤더가 한국어로 설정되면 한국어 메시지가 반환된다.")
    fun test2() {
        timeManagerFixture.changeCurrentTime(timeFixture())

        mockMvc
            .perform(
                get("/api/v1/test/message")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer ${generateAccessTokenValue()}")
                    .header("Accept-Language", "ko")
            )  // 한국어를 우선으로 설정
            .andDo(print())
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.locale").value(Locale.KOREAN.toString()),
                jsonPath("$.code").value("Example"),
                jsonPath("$.message").value("예제 메시지"),
                jsonPath("$.description").value("예제 설명(args=1,2,3)"),
            )
    }

    @Test
    @DisplayName("Accept Language 헤더가 여러 언어를 포함하고, 한국어가 우선시되면 한국어 응답이 나간다.")
    fun test3() {
        timeManagerFixture.changeCurrentTime(timeFixture())

        mockMvc
            .perform(
                get("/api/v1/test/message")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer ${generateAccessTokenValue()}")
                    .header("Accept-Language", "ko;q=0.9, en;q=0.8")
            )  // 한국어를 우선
            .andDo(print())
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.locale").value(Locale.KOREAN.toString()),
                jsonPath("$.code").value("Example"),
                jsonPath("$.message").value("예제 메시지"),
                jsonPath("$.description").value("예제 설명(args=1,2,3)"),
            )
    }

    @Test
    @DisplayName("Accept Language 헤더가 영어로 설정되면 영어 응답이 나간다.")
    fun test4() {
        timeManagerFixture.changeCurrentTime(timeFixture())

        mockMvc
            .perform(
                get("/api/v1/test/message")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer ${generateAccessTokenValue()}")
                    .header("Accept-Language", "en")
            )  // 영어를 우선
            .andDo(print())
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.locale").value(Locale.ENGLISH.toString()),
                jsonPath("$.code").value("Example"),
                jsonPath("$.message").value("Example Message"),
                jsonPath("$.description").value("Example Description(args=1,2,3)"),
            )
    }

    @Test
    @DisplayName("Accept Language 헤더가 여러 언어를 포함하고, 영어가 우선시되면 영어 응답이 전송된다.")
    fun test5() {
        timeManagerFixture.changeCurrentTime(timeFixture())

        mockMvc
            .perform(
                get("/api/v1/test/message")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer ${generateAccessTokenValue()}")
                    .header("Accept-Language", "en;q=0.9, ko;q=0.8")
            )  // 영어를 우선
            .andDo(print())
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.locale").value(Locale.ENGLISH.toString()),
                jsonPath("$.code").value("Example"),
                jsonPath("$.message").value("Example Message"),
                jsonPath("$.description").value("Example Description(args=1,2,3)"),
            )
    }
}
