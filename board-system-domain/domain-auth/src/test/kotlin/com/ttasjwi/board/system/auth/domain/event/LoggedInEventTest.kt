package com.ttasjwi.board.system.auth.domain.event

import com.ttasjwi.board.system.auth.domain.event.fixture.loggedInEventFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LoggedInEvent: 로그인됨 이벤트")
class LoggedInEventTest {

    @Test
    @DisplayName("생성 시점에 전달한 값을 가진다")
    fun test() {
        // given
        val accessToken = "accessToken1234"
        val refreshToken = "refreshToken45676"
        val accessTokenExpiresAt = timeFixture(minute = 5)
        val refreshTokenExpiresAt = timeFixture(dayOfMonth = 30)
        val loggedInAt = timeFixture(minute = 0)

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
