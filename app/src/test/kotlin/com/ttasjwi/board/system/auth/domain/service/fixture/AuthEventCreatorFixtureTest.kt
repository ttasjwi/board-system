package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.fixture.accessTokenFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.global.auth.Role
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("AuthEventCreator 픽스쳐 테스트")
class AuthEventCreatorFixtureTest {

    private lateinit var authEventCreatorFixture: AuthEventCreatorFixture

    @BeforeEach
    fun setup() {
        authEventCreatorFixture = AuthEventCreatorFixture()
    }

    @Nested
    @DisplayName("onLoginSuccess: 로그인됨 이벤트를 생성함")
    inner class OnLoginSuccess {


        @Test
        @DisplayName("로그인됨 이벤트가 생성된다")
        fun test() {
            // given
            val memberId = 1L
            val role = Role.ADMIN
            val loggedInAt = appDateTimeFixture(minute = 5)

            val accessToken = accessTokenFixture(
                memberId = memberId,
                role = role,
                issuedAt = loggedInAt,
                tokenValue = "accessToken"
            )
            val refreshToken = refreshTokenFixture(
                memberId = memberId,
                refreshTokenId = "refreshTokenId1",
                tokenValue = "toeknValue1",
                issuedAt = loggedInAt
            )

            // when
            val event = authEventCreatorFixture.onLoginSuccess(
                accessToken = accessToken,
                refreshToken = refreshToken,
            )

            // then
            val data = event.data

            assertThat(event.occurredAt).isEqualTo(loggedInAt)
            assertThat(data.accessToken).isEqualTo(accessToken.tokenValue)
            assertThat(data.accessTokenExpiresAt).isEqualTo(accessToken.expiresAt)
            assertThat(data.refreshToken).isEqualTo(refreshToken.tokenValue)
            assertThat(data.refreshTokenExpiresAt).isEqualTo(refreshToken.expiresAt)
        }
    }

    @Nested
    @DisplayName("onTokenRefreshed: 토큰 재생신됨 이벤트를 생성한다")
    inner class OnTokenRefreshed {


        @Test
        @DisplayName("토큰 재갱신됨 이벤트가 생성된다")
        fun test() {
            // given
            val memberId = 1L
            val role = Role.ADMIN
            val tokenRefreshedAt = appDateTimeFixture(minute = 5)
            val refreshTokenRefreshed = true

            val accessToken = accessTokenFixture(
                memberId = memberId,
                role = role,
                issuedAt = tokenRefreshedAt,
                tokenValue = "accessToken"
            )
            val refreshToken = refreshTokenFixture(
                memberId = memberId,
                refreshTokenId = "refreshTokenId1",
                tokenValue = "toeknValue1",
                issuedAt = tokenRefreshedAt
            )

            // when
            val event = authEventCreatorFixture.onTokenRefreshed(
                accessToken = accessToken,
                refreshToken = refreshToken,
                refreshTokenRefreshed = refreshTokenRefreshed
            )

            // then
            val data = event.data

            assertThat(event.occurredAt).isEqualTo(tokenRefreshedAt)
            assertThat(data.accessToken).isEqualTo(accessToken.tokenValue)
            assertThat(data.accessTokenExpiresAt).isEqualTo(accessToken.expiresAt)
            assertThat(data.refreshToken).isEqualTo(refreshToken.tokenValue)
            assertThat(data.refreshTokenExpiresAt).isEqualTo(refreshToken.expiresAt)
            assertThat(data.refreshTokenRefreshed).isEqualTo(refreshTokenRefreshed)
        }
    }
}
