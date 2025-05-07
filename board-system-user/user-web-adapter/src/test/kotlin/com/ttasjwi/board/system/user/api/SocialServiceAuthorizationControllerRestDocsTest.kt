package com.ttasjwi.board.system.user.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.test.util.*
import com.ttasjwi.board.system.user.domain.SocialServiceAuthorizationUseCase
import com.ttasjwi.board.system.user.test.base.UserRestDocsTest
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DisplayName("[user-web-adapter] SocialServiceAuthorizationController 문서화 테스트")
@WebMvcTest(SocialServiceAuthorizationController::class)
class SocialServiceAuthorizationControllerRestDocsTest : UserRestDocsTest() {

    @MockkBean
    private lateinit var useCase: SocialServiceAuthorizationUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val urlPattern = "/api/v1/auth/social-service-authorization/{socialServiceId}"
        val authorizationRequestUri =
            "https://accounts.google.com/o/oauth2/v2/auth?client_id={clientId}&redirect_uri={redirectUri}&response_type={responseType}&scope={scope}&state={state}&code_challenge={codeChallenge}&code_challenge_method={codeChallengeMethod}&nonce={nonce}"
        every { useCase.generateAuthorizationRequestUri("google") } returns authorizationRequestUri

        mockMvc
            .perform(request(HttpMethod.GET, urlPattern, "google"))
            .andExpectAll(
                status().`is`(HttpStatus.FOUND.value()),
                header().string(HttpHeaders.LOCATION, authorizationRequestUri)
            )
            .andDocument(
                identifier = "social-service-authorization-success",
                pathParameters(
                    "socialServiceId"
                            paramMeans  "소셜서비스 식별자(naver, kakao, google 중 하나)"
                ),
                responseHeaders(
                    HttpHeaders.LOCATION
                            headerMeans "소셜서비스 인가 요청 URL"
                )
            )
    }
}
