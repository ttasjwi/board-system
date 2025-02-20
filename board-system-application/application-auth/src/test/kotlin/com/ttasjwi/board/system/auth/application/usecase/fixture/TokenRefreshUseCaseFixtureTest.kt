package com.ttasjwi.board.system.auth.application.usecase.fixture

import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshRequest
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshUseCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("TokenRefreshUseCaseFixture 테스트")
class TokenRefreshFixtureTest {

    private lateinit var useCase: TokenRefreshUseCase

    @BeforeEach
    fun setup() {
        useCase = TokenRefreshUseCaseFixture()
    }

    @Test
    @DisplayName("토큰 재갱신 후 결과를 반환한다.")
    fun test() {
        // given
        val request = TokenRefreshRequest(refreshToken = "refreshToken")

        // when
        val result = useCase.tokenRefresh(request)

        // then
        assertThat(result.accessToken).isNotNull()
        assertThat(result.accessTokenExpiresAt).isNotNull()
        assertThat(result.refreshToken).isNotNull()
        assertThat(result.refreshTokenExpiresAt).isNotNull()
        assertThat(result.refreshTokenRefreshed).isNotNull()
    }
}
