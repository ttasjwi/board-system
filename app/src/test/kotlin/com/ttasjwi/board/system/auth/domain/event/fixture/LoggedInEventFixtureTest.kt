package com.ttasjwi.board.system.auth.domain.event.fixture

import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LoggedInEvent 픽스쳐 테스트")
class LoggedInEventFixtureTest {

    @Test
    @DisplayName("기본값 테스트")
    fun test1() {
        // given
        // when
        val event = loggedInEventFixture()

        // then
        val data = event.data

        assertThat(event.occurredAt).isNotNull()
        assertThat(data.accessToken).isNotNull()
        assertThat(data.accessTokenExpiresAt).isNotNull()
        assertThat(data.refreshToken).isNotNull()
        assertThat(data.refreshTokenExpiresAt).isNotNull()
    }

    @Test
    @DisplayName("커스텀 파라미터 테스트")
    fun test2() {
        // given
        val accessToken = "accessToken1234"
        val refreshToken = "refreshToken45676"
        val accessTokenExpiresAt = appDateTimeFixture(minute = 5)
        val refreshTokenExpiresAt = appDateTimeFixture(dayOfMonth = 30)
        val loggedInAt = appDateTimeFixture(minute = 0)

        // when
        val event = loggedInEventFixture(
            accessToken = accessToken,
            accessTokenExpiresAt = accessTokenExpiresAt,
            refreshToken = refreshToken,
            refreshTokenExpiresAt = refreshTokenExpiresAt,
            loggedInAt = loggedInAt,
        )

        // then
        val data = event.data

        assertThat(event.occurredAt).isEqualTo(loggedInAt)
        assertThat(data.accessToken).isEqualTo(accessToken)
        assertThat(data.accessTokenExpiresAt).isEqualTo(accessTokenExpiresAt)
        assertThat(data.refreshToken).isEqualTo(refreshToken)
        assertThat(data.refreshTokenExpiresAt).isEqualTo(refreshTokenExpiresAt)
    }
}
