package com.ttasjwi.board.system.auth.application.processor

import com.ttasjwi.board.system.auth.application.dto.TokenRefreshCommand
import com.ttasjwi.board.system.auth.domain.exception.RefreshTokenExpiredException
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
import com.ttasjwi.board.system.auth.domain.service.fixture.*
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("TokenRefreshProcessor: 토큰재갱신 애플리케이션 명령을 실질적으로 처리한다")
class TokenRefreshProcessorTest {

    private lateinit var processor: TokenRefreshProcessor
    private lateinit var refreshTokenHolderStorageFixture: RefreshTokenHolderStorageFixture
    private lateinit var refreshTokenManagerFixture: RefreshTokenManagerFixture
    private var memberId: Long = 0L

    @BeforeEach
    fun setup() {
        refreshTokenHolderStorageFixture = RefreshTokenHolderStorageFixture()
        refreshTokenManagerFixture = RefreshTokenManagerFixture()
        processor = TokenRefreshProcessor(
            refreshTokenManager = refreshTokenManagerFixture,
            refreshTokenHolderFinder = refreshTokenHolderStorageFixture,
            accessTokenGenerator = AccessTokenGeneratorFixture(),
            refreshTokenHolderManager = RefreshTokenHolderManagerFixture(),
            refreshTokenHolderAppender = refreshTokenHolderStorageFixture,
            authEventCreator = AuthEventCreatorFixture()
        )
        memberId = 133L
    }

    @Test
    @DisplayName("리프레시토큰을 재갱신하지 않아도 될 경우 리프레시 토큰이 갱신되지 않고 액세스토큰만 갱신된다.")
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
        val command = TokenRefreshCommand(refreshToken.tokenValue, currentTime)

        // when
        val event = processor.tokenRefresh(command)

        // then
        val data = event.data
        val findRefreshTokenHolder = refreshTokenHolderStorageFixture.findByMemberIdOrNull(memberId)!!
        val tokens = findRefreshTokenHolder.getTokens()

        assertThat(event.occurredAt).isEqualTo(command.currentTime)
        assertThat(data.accessToken).isNotNull()
        assertThat(data.accessTokenExpiresAt).isNotNull()
        assertThat(data.refreshToken).isEqualTo(refreshToken.tokenValue)
        assertThat(data.refreshTokenExpiresAt).isEqualTo(refreshToken.expiresAt)
        assertThat(data.refreshTokenRefreshed).isFalse()
        assertThat(findRefreshTokenHolder.authMember.memberId).isEqualTo(refreshTokenHolder.authMember.memberId)
        assertThat(findRefreshTokenHolder.authMember.role).isEqualTo(refreshTokenHolder.authMember.role)
        assertThat(tokens.size).isEqualTo(1)
        assertThat(tokens.values).containsExactly(refreshToken)
    }

    @Test
    @DisplayName("리프레시토큰을 재갱신해야할 경우 리프레시 토큰이 함께 재갱신된다.")
    fun testRefreshTokenRefresh() {
        // given
        val refreshToken = refreshTokenManagerFixture.generate(memberId, appDateTimeFixture())
        val refreshTokenHolder = refreshTokenHolderFixture(
            memberId = memberId,
            tokens = mutableMapOf(refreshToken.refreshTokenId to refreshToken)
        )
        refreshTokenHolderStorageFixture.append(memberId, refreshTokenHolder, refreshToken.issuedAt)

        val currentTime =
            appDateTimeFixture(
                hour = (RefreshTokenManagerFixture.VALIDITY_HOURS
                        - RefreshTokenManagerFixture.REFRESH_REQUIRE_THRESHOLD_HOURS).toInt()
            ).plusSeconds(1)
        val command = TokenRefreshCommand(refreshToken.tokenValue, currentTime)

        // when
        val event = processor.tokenRefresh(command)

        // then
        val data = event.data
        val findRefreshTokenHolder = refreshTokenHolderStorageFixture.findByMemberIdOrNull(memberId)!!
        val tokens = findRefreshTokenHolder.getTokens()

        assertThat(event.occurredAt).isEqualTo(command.currentTime)
        assertThat(data.accessToken).isNotNull()
        assertThat(data.accessTokenExpiresAt).isNotNull()
        assertThat(data.refreshToken).isNotEqualTo(refreshToken.tokenValue)
        assertThat(data.refreshTokenExpiresAt).isNotNull()
        assertThat(data.refreshTokenRefreshed).isTrue()
        assertThat(findRefreshTokenHolder.authMember.memberId).isEqualTo(refreshTokenHolder.authMember.memberId)
        assertThat(findRefreshTokenHolder.authMember.role).isEqualTo(refreshTokenHolder.authMember.role)
        assertThat(tokens.size).isEqualTo(1)
        assertThat(tokens.values).doesNotContain(refreshToken)
    }

    @Test
    @DisplayName("리프레시토큰 홀더가 조회되지 않을 경우 예외가 발생한다.")
    fun testRefreshTokenHolderNotFound() {
        // given
        val refreshToken = refreshTokenManagerFixture.generate(memberId, appDateTimeFixture())
        val command = TokenRefreshCommand(
            refreshToken = refreshToken.tokenValue,
            currentTime = appDateTimeFixture(dayOfMonth = 4)
        )

        // when
        val ex = assertThrows<RefreshTokenExpiredException> { processor.tokenRefresh(command) }

        // then
        assertThat(ex.debugMessage).isEqualTo("리프레시 토큰 홀더가 조회되지 않았음. 따라서 리프레시토큰이 만료된 것으로 간주됨 (리프레시토큰 만료시각=${refreshToken.expiresAt},현재시각=${command.currentTime})")
    }

}
