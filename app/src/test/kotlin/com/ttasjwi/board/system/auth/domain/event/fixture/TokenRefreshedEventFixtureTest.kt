package com.ttasjwi.board.system.auth.domain.event.fixture

import com.ttasjwi.board.system.common.time.fixture.timeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("TokenRefreshedEventFixture: 토큰 재갱신됨 이벤트 픽스쳐")
class TokenRefreshedEventFixtureTest {

    @Test
    @DisplayName("기본값 테스트")
    fun test1() {
        // given
        // when
        val event = tokenRefreshedEventFixture()
        // then
        val data = event.data

        assertThat(event.occurredAt).isNotNull()
        assertThat(data.accessToken).isNotNull()
        assertThat(data.accessTokenExpiresAt).isNotNull()
        assertThat(data.refreshToken).isNotNull()
        assertThat(data.refreshTokenExpiresAt).isNotNull()
        assertThat(data.refreshTokenRefreshed).isFalse()
    }

    @Test
    @DisplayName("커스텀 파라미터 테스트")
    fun test2() {
        // given
        val accessToken = "accessToken1234"
        val refreshToken = "refreshToken45676"
        val accessTokenExpiresAt = timeFixture(minute = 30)
        val refreshTokenExpiresAt = timeFixture(dayOfMonth = 2)
        val refreshTokenRefreshed = true
        val refreshedAt = timeFixture(minute = 0)

        // when
        val event = tokenRefreshedEventFixture(
            accessToken = accessToken,
            accessTokenExpiresAt = accessTokenExpiresAt,
            refreshToken = refreshToken,
            refreshTokenExpiresAt = refreshTokenExpiresAt,
            refreshedAt = refreshedAt,
            refreshTokenRefreshed = refreshTokenRefreshed,
        )

        // then
        val data = event.data

        assertThat(event.occurredAt).isEqualTo(refreshedAt)
        assertThat(data.accessToken).isEqualTo(accessToken)
        assertThat(data.accessTokenExpiresAt).isEqualTo(accessTokenExpiresAt)
        assertThat(data.refreshToken).isEqualTo(refreshToken)
        assertThat(data.refreshTokenExpiresAt).isEqualTo(refreshTokenExpiresAt)
        assertThat(data.refreshTokenRefreshed).isEqualTo(refreshTokenRefreshed)
    }
}
