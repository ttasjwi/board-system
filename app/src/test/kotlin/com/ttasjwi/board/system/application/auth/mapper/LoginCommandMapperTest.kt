package com.ttasjwi.board.system.application.auth.mapper

import com.ttasjwi.board.system.application.auth.usecase.LoginRequest
import com.ttasjwi.board.system.global.exception.NullArgumentException
import com.ttasjwi.board.system.global.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.global.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("LoginCommandMapper: 로그인 요청을 애플리케이션 명령으로 변환")
class LoginCommandMapperTest {

    private lateinit var commandMapper: LoginCommandMapper
    private lateinit var timeManagerFixture: TimeManagerFixture

    @BeforeEach
    fun setup() {
        timeManagerFixture = TimeManagerFixture()
        commandMapper = LoginCommandMapper(
            timeManager = timeManagerFixture
        )
    }

    @Test
    @DisplayName("성공테스트")
    fun testSuccess() {
        // given
        timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 0))

        val email = "hello@gmail.com"
        val password = "1234"
        val request = LoginRequest(email, password)

        // when
        val command = commandMapper.mapToCommand(request)

        // then
        assertThat(command.email).isEqualTo(email)
        assertThat(command.rawPassword).isEqualTo(password)
        assertThat(command.currentTime).isEqualTo(appDateTimeFixture(minute = 0))
    }

    @Test
    @DisplayName("email 이 null 이면 예외가 발생한다")
    fun testEmailNull() {
        val request = LoginRequest(email = null, password = "1234")

        val exceptions =
            assertThrows<ValidationExceptionCollector> { commandMapper.mapToCommand(request) }.getExceptions()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(NullArgumentException::class.java)
        assertThat(exceptions[0].source).isEqualTo("email")
    }

    @Test
    @DisplayName("패스워드가 null 이면 예외가 발생한다")
    fun testPasswordNull() {
        val request = LoginRequest(email = "ttasjwi", password = null)

        val exceptions =
            assertThrows<ValidationExceptionCollector> { commandMapper.mapToCommand(request) }.getExceptions()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(NullArgumentException::class.java)
        assertThat(exceptions[0].source).isEqualTo("password")
    }
}
