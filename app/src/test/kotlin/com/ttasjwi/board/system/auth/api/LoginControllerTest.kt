package com.ttasjwi.board.system.auth.api

import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.auth.application.usecase.fixture.LoginUseCaseFixture
import com.ttasjwi.board.system.common.api.SuccessResponse
import com.ttasjwi.board.system.common.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*

@DisplayName("LoginController 테스트")
class LoginControllerTest {

    private lateinit var controller: LoginController

    @BeforeEach
    fun setup() {
        controller = LoginController(
            useCase = LoginUseCaseFixture(),
            messageResolver = MessageResolverFixture(),
            localeManager = LocaleManagerFixture()
        )
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 그 결과를 기반으로 200 코드와 함께 응답을 반환한다.")
    fun test() {
        // given
        val request = LoginRequest(
            email = "test@test.com",
            password = "1111",
        )

        // when
        val responseEntity = controller.login(request)
        val response = responseEntity.body as SuccessResponse<LoginResponse>

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.isSuccess).isTrue()
        assertThat(response.code).isEqualTo("Login.Complete")
        assertThat(response.message).isEqualTo("Login.Complete.message(locale=${Locale.KOREAN},args=[])")
        assertThat(response.description).isEqualTo("Login.Complete.description(locale=${Locale.KOREAN},args=[])")

        val loginResult = response.data.loginResult

        assertThat(loginResult.accessToken).isNotNull()
        assertThat(loginResult.accessTokenExpiresAt).isNotNull()
        assertThat(loginResult.tokenType).isEqualTo(LoginController.TOKEN_TYPE)
        assertThat(loginResult.refreshToken).isNotNull()
        assertThat(loginResult.refreshTokenExpiresAt).isNotNull()
    }
}
