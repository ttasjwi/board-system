package com.ttasjwi.board.system.domain.fixture

import com.ttasjwi.board.system.user.domain.LoginRequest
import com.ttasjwi.board.system.user.domain.LoginUseCase
import com.ttasjwi.board.system.user.domain.fixture.LoginUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LoginUseCaseFixture 테스트")
class LoginUseCaseFixtureTest {

    private lateinit var useCase: LoginUseCase

    @BeforeEach
    fun setup() {
        useCase = LoginUseCaseFixture()
    }

    @Test
    @DisplayName("로그인 후 로그인 결과를 반환한다.")
    fun test() {
        // given
        val request = LoginRequest(
            email = "test@gmail.com",
            password = "1234",
        )

        // when
        val result = useCase.login(request)

        // then
        assertThat(result.accessToken).isNotNull()
        assertThat(result.accessTokenType).isEqualTo("Bearer")
        assertThat(result.accessTokenExpiresAt).isNotNull()
        assertThat(result.refreshToken).isNotNull()
        assertThat(result.refreshTokenExpiresAt).isNotNull()
    }
}
