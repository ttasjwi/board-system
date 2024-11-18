package com.ttasjwi.board.system.auth.api

import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshRequest
import com.ttasjwi.board.system.auth.application.usecase.fixture.TokenRefreshUseCaseFixture
import com.ttasjwi.board.system.core.api.SuccessResponse
import com.ttasjwi.board.system.core.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.core.message.fixture.MessageResolverFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*

@DisplayName("TokenRefreshController 테스트")
class TokenRefreshControllerTest {

    private lateinit var controller: TokenRefreshController

    @BeforeEach
    fun setup() {
        controller = TokenRefreshController(
            useCase = TokenRefreshUseCaseFixture(),
            messageResolver = MessageResolverFixture(),
            localeManager = LocaleManagerFixture()
        )
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 그 결과를 기반으로 200 코드와 함께 응답을 반환한다.")
    fun test() {
        // given
        val request = TokenRefreshRequest("token")

        // when
        val responseEntity = controller.login(request)
        val response = responseEntity.body as SuccessResponse<TokenRefreshResponse>

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.isSuccess).isTrue()
        assertThat(response.code).isEqualTo("TokenRefresh.Complete")
        assertThat(response.message).isEqualTo("TokenRefresh.Complete.message(locale=${Locale.KOREAN},args=[])")
        assertThat(response.description).isEqualTo("TokenRefresh.Complete.description(locale=${Locale.KOREAN},args=[])")

        val tokenRefreshResult = response.data.tokenRefreshResult

        assertThat(tokenRefreshResult.accessToken).isNotNull()
        assertThat(tokenRefreshResult.accessTokenExpiresAt).isNotNull()
        assertThat(tokenRefreshResult.tokenType).isEqualTo(LoginController.TOKEN_TYPE)
        assertThat(tokenRefreshResult.refreshToken).isNotNull()
        assertThat(tokenRefreshResult.refreshTokenExpiresAt).isNotNull()
        assertThat(tokenRefreshResult.refreshTokenExpiresAt).isNotNull()
    }
}
