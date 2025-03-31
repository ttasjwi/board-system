package com.ttasjwi.board.system.spring.security.oauth2.redis

import com.ttasjwi.board.system.IntegrationTest
import io.mockk.every
import io.mockk.mockk
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.core.endpoint.fixture.oAuth2AuthorizationRequestFixture

@DisplayName("RedisOAuth2AuthorizationRequestRepository 테스트")
class RedisOAuth2AuthorizationRequestRepositoryTest : IntegrationTest() {

    @Nested
    @DisplayName("saveAuthorizationRequest")
    inner class SaveAndLoad {

        @Test
        @DisplayName("저장 후 조회 요청 시 state 가 같을 때, 잘 찾아진다.")
        fun test1() {
            // given
            val authorizationRequest = oAuth2AuthorizationRequestFixture(
                state = "state123456"
            )
            val request = mockk<HttpServletRequest>()
            val response = mockk<HttpServletResponse>()

            redisOAuth2AuthorizationRequestRepository.saveAuthorizationRequest(authorizationRequest, request, response)
            every { request.getParameter(OAuth2ParameterNames.STATE) } returns "state123456"

            // when
            val loadedAuthorizationRequest =
                redisOAuth2AuthorizationRequestRepository.loadAuthorizationRequest(request)!!

            // then
            assertThat(loadedAuthorizationRequest.state).isEqualTo("state123456")
        }
    }

}
