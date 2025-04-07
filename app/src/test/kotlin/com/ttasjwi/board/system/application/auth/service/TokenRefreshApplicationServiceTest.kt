package com.ttasjwi.board.system.application.auth.service

import com.ttasjwi.board.system.application.auth.mapper.TokenRefreshCommandMapper
import com.ttasjwi.board.system.application.auth.processor.TokenRefreshProcessor
import com.ttasjwi.board.system.application.auth.usecase.TokenRefreshRequest
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
import com.ttasjwi.board.system.auth.domain.service.fixture.*
import com.ttasjwi.board.system.global.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("TokenRefreshApplicationService 테스트")
class TokenRefreshApplicationServiceTest {

    private lateinit var applicationService: TokenRefreshApplicationService
    private lateinit var refreshTokenHolderStorageFixture: RefreshTokenHolderStorageFixture
    private lateinit var refreshTokenManagerFixture: RefreshTokenManagerFixture
    private lateinit var timeManagerFixture: TimeManagerFixture
    private var memberId: Long = 0L

    @BeforeEach
    fun setup() {
        refreshTokenHolderStorageFixture = RefreshTokenHolderStorageFixture()
        refreshTokenManagerFixture = RefreshTokenManagerFixture()
        timeManagerFixture = TimeManagerFixture()
        applicationService = TokenRefreshApplicationService(
            commandMapper = TokenRefreshCommandMapper(timeManagerFixture),
            processor = TokenRefreshProcessor(
                refreshTokenManager = refreshTokenManagerFixture,
                refreshTokenHolderFinder = refreshTokenHolderStorageFixture,
                accessTokenManager = AccessTokenManagerFixture(),
                refreshTokenHolderManager = RefreshTokenHolderManagerFixture(),
                refreshTokenHolderAppender = refreshTokenHolderStorageFixture,
                authEventCreator = AuthEventCreatorFixture()
            ),
        )
        memberId = 147L
    }


    @Test
    @DisplayName("리프레시토큰을 만료하지 않아도 되는 경우, 리프레시 토큰이 갱신되지 않은 결과가 반환되는 지 테스트")
    fun testRefreshTokenNotRefresh() {
        // given
        val refreshToken = refreshTokenManagerFixture.generate(memberId, appDateTimeFixture())
        val refreshTokenHolder = refreshTokenHolderFixture(
            memberId = memberId,
            tokens = mutableMapOf(refreshToken.refreshTokenId to refreshToken)
        )
        refreshTokenHolderStorageFixture.append(memberId, refreshTokenHolder, refreshToken.issuedAt)

        val currentTime = appDateTimeFixture(
            hour = (RefreshTokenManagerFixture.VALIDITY_HOURS
                    - RefreshTokenManagerFixture.REFRESH_REQUIRE_THRESHOLD_HOURS).toInt()
        )
            .minusSeconds(1)
        timeManagerFixture.changeCurrentTime(currentTime)
        val request = TokenRefreshRequest(refreshToken.tokenValue)

        // when
        val response = applicationService.tokenRefresh(request)

        // then
        assertThat(response.accessToken).isNotNull()
        assertThat(response.accessTokenType).isEqualTo("Bearer")
        assertThat(response.accessTokenExpiresAt).isNotNull()
        assertThat(response.refreshToken).isEqualTo(refreshToken.tokenValue)
        assertThat(response.refreshTokenExpiresAt).isEqualTo(refreshToken.expiresAt.toZonedDateTime())
        assertThat(response.refreshTokenRefreshed).isFalse()
    }
}
