package com.ttasjwi.board.system.support.mvc

import com.nimbusds.jose.util.StandardCharset
import com.ttasjwi.board.system.IntegrationTest
import com.ttasjwi.board.system.auth.domain.exception.AccessTokenExpiredException
import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.fixture.timeFixture
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@DisplayName("MVC 통합 인증/인가 테스트")
class MvcTestControllerTest : IntegrationTest() {

    @Test
    @DisplayName("유효한 시간 내에 액세스토큰을 전달하면 필터를 통과한다.")
    fun testAuthenticated() {
        // given
        val authMember = authMemberFixture(memberId = 5544, role = Role.USER)
        val accessToken = accessTokenManager.generate(authMember, timeFixture(minute = 5))
        timeManagerFixture.changeCurrentTime(timeFixture(minute = 18))

        mockMvc
            .perform(
                get("/api/v1/test/authenticated")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer ${accessToken.tokenValue}")
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
        val accessToken = accessTokenManager.generate(authMember, timeFixture(minute = 5))
        timeManagerFixture.changeCurrentTime(timeFixture(minute = 45))

        assertThrows<AccessTokenExpiredException> {
            mockMvc
                .perform(
                    get("/api/v1/test/authenticated")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer ${accessToken.tokenValue}")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
        }
    }
}
