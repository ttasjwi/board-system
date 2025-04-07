package com.ttasjwi.board.system.api.controller.auth

import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.auth.application.usecase.LoginResponse
import com.ttasjwi.board.system.auth.application.usecase.fixture.LoginUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("LoginController 테스트")
class LoginControllerTest {

    private lateinit var controller: LoginController

    @BeforeEach
    fun setup() {
        controller = LoginController(LoginUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 성공하면, 200 응답을 반환한다.")
    fun test() {
        // given
        val request = LoginRequest(
            email = "test@test.com",
            password = "1111",
        )

        // when
        val responseEntity = controller.login(request)
        val response = responseEntity.body as LoginResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())

        assertThat(response.accessToken).isNotNull()
        assertThat(response.accessTokenExpiresAt).isNotNull()
        assertThat(response.accessTokenType).isEqualTo("Bearer")
        assertThat(response.refreshToken).isNotNull()
        assertThat(response.refreshTokenExpiresAt).isNotNull()
    }
}
