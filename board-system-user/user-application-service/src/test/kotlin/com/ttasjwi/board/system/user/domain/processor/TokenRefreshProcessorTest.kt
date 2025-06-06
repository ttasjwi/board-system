package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.auth.RefreshTokenExpiredException
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.dto.TokenRefreshCommand
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.model.fixture.userFixture
import com.ttasjwi.board.system.user.domain.port.fixture.UserRefreshTokenIdListPersistencePortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.RefreshTokenIdPersistencePortFixture
import com.ttasjwi.board.system.user.domain.service.RefreshTokenHandler
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("TokenRefreshProcessor: 토큰 재갱신 애플리케이션 명령을 실질적으로 처리한다.")
class TokenRefreshProcessorTest {

    private lateinit var processor: TokenRefreshProcessor
    private lateinit var refreshTokenHandler: RefreshTokenHandler
    private lateinit var userRefreshTokenIdListPersistencePortFixture: UserRefreshTokenIdListPersistencePortFixture
    private lateinit var refreshTokenIdPersistencePortFixture: RefreshTokenIdPersistencePortFixture
    private lateinit var user: User

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()

        refreshTokenHandler = container.refreshTokenHandler
        userRefreshTokenIdListPersistencePortFixture = container.userRefreshTokenIdListPersistencePortFixture
        refreshTokenIdPersistencePortFixture = container.refreshTokenIdPersistencePortFixture
        processor = container.tokenRefreshProcessor
        user = container.userPersistencePortFixture.save(
            userFixture(
                userId = 145688L,
                role = Role.ADMIN,
            )
        )
    }

    @Test
    @DisplayName("리프레시토큰을 재갱신하지 않아도 될 경우 리프레시 토큰이 갱신되지 않고 액세스토큰만 갱신된다.")
    fun testRefreshTokenNotRefresh() {
        // given
        val refreshToken = refreshTokenHandler.createAndPersist(user.userId, appDateTimeFixture())

        val currentTime = appDateTimeFixture(
            hour = (RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR
                    - RefreshTokenHandler.REFRESH_REQUIRE_THRESHOLD_HOURS).toInt()
        )
            .minusSeconds(1)

        val command = TokenRefreshCommand(refreshToken.tokenValue, currentTime)

        // when
        val (accessToken, newRefreshToken) = processor.tokenRefresh(command)

        // then
        val userRefreshTokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(user.userId)
        val isPrevRefreshTokenExists = refreshTokenIdPersistencePortFixture.exists(user.userId, refreshToken.refreshTokenId)

        assertThat(accessToken.authUser.userId).isEqualTo(refreshToken.userId)
        assertThat(accessToken.authUser.role).isEqualTo(user.role)
        assertThat(accessToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(accessToken.expiresAt).isEqualTo(command.currentTime.plusMinutes(30))

        assertThat(newRefreshToken.userId).isEqualTo(refreshToken.userId)
        assertThat(newRefreshToken.refreshTokenId).isEqualTo(refreshToken.refreshTokenId)
        assertThat(newRefreshToken.tokenValue).isEqualTo(refreshToken.tokenValue)
        assertThat(newRefreshToken.issuedAt).isEqualTo(refreshToken.issuedAt)
        assertThat(newRefreshToken.expiresAt).isEqualTo(refreshToken.expiresAt)

        assertThat(userRefreshTokenIds).containsExactly(refreshToken.refreshTokenId)
        assertThat(isPrevRefreshTokenExists).isTrue()
    }

    @Test
    @DisplayName("리프레시토큰을 재갱신해야할 경우 리프레시 토큰이 함께 재갱신된다.")
    fun testRefreshTokenRefresh() {
        // given
        val refreshToken = refreshTokenHandler.createAndPersist(user.userId, appDateTimeFixture())

        val currentTime = appDateTimeFixture(
            hour = (RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR
                    - RefreshTokenHandler.REFRESH_REQUIRE_THRESHOLD_HOURS).toInt()
        )
            .plusSeconds(1)

        val command = TokenRefreshCommand(refreshToken.tokenValue, currentTime)

        // when
        val (accessToken, newRefreshToken) = processor.tokenRefresh(command)

        // then
        val userRefreshTokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(user.userId)
        val isPrevRefreshTokenExists = refreshTokenIdPersistencePortFixture.exists(user.userId, refreshToken.refreshTokenId)
        val isNewRefreshTokenExists = refreshTokenIdPersistencePortFixture.exists(user.userId, newRefreshToken.refreshTokenId)

        assertThat(accessToken.authUser.userId).isEqualTo(refreshToken.userId)
        assertThat(accessToken.authUser.role).isEqualTo(user.role)
        assertThat(accessToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(accessToken.expiresAt).isEqualTo(command.currentTime.plusMinutes(30))


        assertThat(newRefreshToken.userId).isEqualTo(refreshToken.userId)
        assertThat(newRefreshToken.refreshTokenId).isNotEqualTo(refreshToken.refreshTokenId)
        assertThat(newRefreshToken.tokenValue).isNotEqualTo(refreshToken.tokenValue)
        assertThat(newRefreshToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(newRefreshToken.expiresAt).isEqualTo(command.currentTime.plusHours(RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR))

        assertThat(userRefreshTokenIds).containsExactly(newRefreshToken.refreshTokenId)
        assertThat(isPrevRefreshTokenExists).isFalse()
        assertThat(isNewRefreshTokenExists).isTrue()
    }

    @Test
    @DisplayName("리프레시토큰은 파싱됐으나, 회원이 존재하지 않을 경우 예외가 발생한다.")
    fun testUserNotFound() {
        val refreshToken = refreshTokenHandler.createAndPersist(1343514131L, appDateTimeFixture())

        val command = TokenRefreshCommand(refreshToken.tokenValue, appDateTimeFixture(hour = 5))

        val ex = assertThrows<RefreshTokenExpiredException> {
            processor.tokenRefresh(command)
        }
        assertThat(ex.debugMessage).isEqualTo("회원이 존재하지 않아서, 리프레시토큰이 더 이상 유효하지 않음")
    }
}
