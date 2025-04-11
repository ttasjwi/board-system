package com.ttasjwi.board.system.auth.api

import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshRequest
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshResponse
import com.ttasjwi.board.system.auth.application.usecase.fixture.TokenRefreshUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("TokenRefreshController 테스트")
class TokenRefreshControllerTest {

    private lateinit var controller: TokenRefreshController

    @BeforeEach
    fun setup() {
        controller = TokenRefreshController(TokenRefreshUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 200 응답을 반환한다.")
    fun test() {
        // given
        val request = TokenRefreshRequest("token")

        // when
        val responseEntity = controller.login(request)
        val response = responseEntity.body as TokenRefreshResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.accessToken).isNotNull()
        assertThat(response.accessTokenType).isEqualTo("Bearer")
        assertThat(response.accessTokenExpiresAt).isNotNull()
        assertThat(response.refreshToken).isNotNull()
        assertThat(response.refreshTokenExpiresAt).isNotNull()
        assertThat(response.refreshTokenExpiresAt).isNotNull()
    }
}
