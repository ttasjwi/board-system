package com.ttasjwi.board.system.member.infra.persistence

import com.ttasjwi.board.system.member.infra.test.MemberRedisAdapterTest
import io.mockk.every
import io.mockk.mockk
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames

@DisplayName("RedisOAuth2AuthorizationRequestRepository 테스트")
class CustomOAuth2AuthorizationRequestRepositoryTest : MemberRedisAdapterTest() {

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

            customOAuth2AuthorizationRequestRepository.saveAuthorizationRequest(authorizationRequest, request, response)
            every { request.getParameter(OAuth2ParameterNames.STATE) } returns "state123456"

            // when
            val loadedAuthorizationRequest =
                customOAuth2AuthorizationRequestRepository.loadAuthorizationRequest(request)!!

            // then
            assertThat(loadedAuthorizationRequest.state).isEqualTo("state123456")
        }
    }

}

private fun oAuth2AuthorizationRequestFixture(
    authorizationUri: String = "authorizationUri",
    clientId: String = "clientId",
    redirectUri: String = "redirectUri",
    scopes: Set<String> = setOf("openid", "profile"),
    state: String = "state123456",
    additionalParameters: Map<String, Any> = mapOf("additional1" to "additionalValue1", "additional2" to "additionalValue2"),
    authorizationRequestUri: String = "authorizationUri",
    attributes: Map<String, Any> = mapOf("key1" to "value1", "key2" to "value2")
): OAuth2AuthorizationRequest {
    return OAuth2AuthorizationRequest.authorizationCode()
        .authorizationUri(authorizationUri)
        .clientId(clientId)
        .redirectUri(redirectUri)
        .scopes(scopes)
        .state(state)
        .additionalParameters(additionalParameters)
        .authorizationRequestUri(authorizationRequestUri)
        .attributes(attributes)
        .build()
}
