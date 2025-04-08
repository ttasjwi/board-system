package com.ttasjwi.board.system.integration

import com.nimbusds.jose.util.StandardCharset
import com.ttasjwi.board.system.IntegrationTest
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@DisplayName("MVC 통합 인증/인가 테스트")
class AuthenticationIntegrationTest : IntegrationTest() {

    @Test
    @DisplayName("유효한 시간 내에 액세스토큰을 전달하면 필터를 통과한다.")
    fun testAuthenticated() {
        // given
        val authMember = authMemberFixture(memberId = 5544, role = Role.USER)
        val accessTokenValue = generateAccessTokenValue(
            memberId = authMember.memberId,
            role = authMember.role,
            issuedAt = appDateTimeFixture()
        )
        timeManagerFixture.changeCurrentTime(appDateTimeFixture())

        mockMvc
            .perform(
                get("/api/v1/test/authenticated")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer $accessTokenValue")
                    .characterEncoding(StandardCharset.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.memberId").value(authMember.memberId),
                jsonPath("$.role").value(authMember.role.name)
            )
    }

    @Test
    @DisplayName("유효시간을 경과하면 예외가 발생하고, 인증에 실패한다.")
    fun testFailed() {
        // given
        val authMember = authMemberFixture(memberId = 5544, role = Role.USER)
        val accessTokenValue = generateAccessTokenValue(
            memberId = authMember.memberId,
            role = authMember.role,
            issuedAt = appDateTimeFixture()
        )
        timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 50))

        mockMvc
            .perform(
                get("/api/v1/test/authenticated")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer $accessTokenValue")
                    .characterEncoding(StandardCharset.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpectAll(
                status().isUnauthorized,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.errors[0].code").value("Error.AccessTokenExpired"),
                jsonPath("$.errors[0].message").value("액세스토큰 만료됨"),
                jsonPath("$.errors[0].source").value("accessToken"),
            )
    }

}
