package com.ttasjwi.board.system.auth.application.service

import com.ttasjwi.board.system.auth.application.mapper.TokenRefreshCommandMapper
import com.ttasjwi.board.system.auth.application.processor.TokenRefreshProcessor
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshRequest
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
import com.ttasjwi.board.system.auth.domain.service.fixture.*
import com.ttasjwi.board.system.core.application.fixture.TransactionRunnerFixture
import com.ttasjwi.board.system.core.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.MemberId
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
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
    private lateinit var memberId: MemberId

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
            transactionRunner = TransactionRunnerFixture()
        )
        memberId = memberIdFixture(147L)
    }


    @Test
    @DisplayName("리프레시토큰을 만료하지 않아도 되는 경우, 리프레시 토큰이 갱신되지 않은 결과가 반환되는 지 테스트")
    fun testRefreshTokenNotRefresh() {
        // given
        val refreshToken = refreshTokenManagerFixture.generate(memberId, timeFixture())
        val refreshTokenHolder = refreshTokenHolderFixture(
            memberId = memberId.value,
            tokens = mutableMapOf(refreshToken.refreshTokenId to refreshToken)
        )
        refreshTokenHolderStorageFixture.append(memberId, refreshTokenHolder, refreshToken.issuedAt)

        val currentTime = timeFixture(
            hour = (RefreshTokenManagerFixture.VALIDITY_HOURS
                    - RefreshTokenManagerFixture.REFRESH_REQUIRE_THRESHOLD_HOURS).toInt()
        )
            .minusNanos(1)
        timeManagerFixture.changeCurrentTime(currentTime)
        val request = TokenRefreshRequest(refreshToken.tokenValue)

        // when
        val result = applicationService.tokenRefresh(request)

        // then
        assertThat(result.accessToken).isNotNull()
        assertThat(result.accessTokenExpiresAt).isNotNull()
        assertThat(result.refreshToken).isEqualTo(refreshToken.tokenValue)
        assertThat(result.refreshTokenExpiresAt).isEqualTo(refreshToken.expiresAt)
        assertThat(result.refreshTokenRefreshed).isFalse()
    }
}
