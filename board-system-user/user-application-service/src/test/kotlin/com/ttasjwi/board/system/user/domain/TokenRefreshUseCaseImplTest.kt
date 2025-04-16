package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.model.fixture.userFixture
import com.ttasjwi.board.system.user.domain.service.RefreshTokenHandler
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("TokenRefreshUseCaseImpl 테스트")
class TokenRefreshUseCaseImplTest {

    private lateinit var useCase: TokenRefreshUseCase
    private lateinit var refreshTokenHandler: RefreshTokenHandler
    private lateinit var timeManagerFixture: TimeManagerFixture
    private lateinit var user: User

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        refreshTokenHandler = container.refreshTokenHandler
        timeManagerFixture = container.timeManagerFixture
        useCase = container.tokenRefreshUseCase
        user = container.userPersistencePortFixture.save(
            userFixture(
                userId = 12345L,
                role = Role.ROOT
            )
        )
    }


    @Test
    @DisplayName("리프레시토큰을 만료하지 않아도 되는 경우, 리프레시 토큰이 갱신되지 않은 결과가 반환되는 지 테스트")
    fun testRefreshTokenNotRefresh() {
        // given
        val refreshToken = refreshTokenHandler.createAndPersist(user.userId, appDateTimeFixture())

        val currentTime = appDateTimeFixture(
            hour = (RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR
                    - RefreshTokenHandler.REFRESH_REQUIRE_THRESHOLD_HOURS).toInt()
        ).minusSeconds(1)

        timeManagerFixture.changeCurrentTime(currentTime)
        val request = TokenRefreshRequest(refreshToken.tokenValue)

        // when
        val response = useCase.tokenRefresh(request)

        // then
        assertThat(response.accessToken).isNotNull()
        assertThat(response.accessTokenType).isEqualTo("Bearer")
        assertThat(response.accessTokenExpiresAt).isNotNull()
        assertThat(response.refreshToken).isEqualTo(refreshToken.tokenValue)
        assertThat(response.refreshTokenExpiresAt).isEqualTo(refreshToken.expiresAt.toZonedDateTime())
        assertThat(response.refreshTokenRefreshed).isFalse()
    }
}
