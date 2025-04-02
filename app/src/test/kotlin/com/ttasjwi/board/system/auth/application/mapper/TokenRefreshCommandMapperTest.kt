package com.ttasjwi.board.system.auth.application.mapper

import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshRequest
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("TokenRefreshCommandMapper 테스트")
class TokenRefreshCommandMapperTest {

    private lateinit var commandMapper: TokenRefreshCommandMapper
    private lateinit var timeManagerFixture: TimeManagerFixture

    @BeforeEach
    fun setup() {
        timeManagerFixture = TimeManagerFixture()
        commandMapper = TokenRefreshCommandMapper(timeManagerFixture)
    }

    @Test
    @DisplayName("리프레시토큰이 없으면 예외가 발생한다.")
    fun testNullToken() {
        // given
        val request = TokenRefreshRequest(null)

        // when
        // then
        val ex = assertThrows<NullArgumentException> { commandMapper.mapToCommand(request) }

        assertThat(ex.source).isEqualTo("refreshToken")
    }

    @Test
    @DisplayName("리프레시토큰값이 있으면 성공한다.")
    fun testSuccess() {
        // given
        timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 30))
        val request = TokenRefreshRequest("token")

        // when
        val command = commandMapper.mapToCommand(request)

        // then
        assertThat(command.refreshToken).isEqualTo(request.refreshToken)
        assertThat(command.currentTime).isEqualTo(appDateTimeFixture(minute = 30))
    }
}
