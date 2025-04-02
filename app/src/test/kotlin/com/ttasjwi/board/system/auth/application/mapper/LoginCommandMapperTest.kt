package com.ttasjwi.board.system.auth.application.mapper

import com.ttasjwi.board.system.auth.application.exception.LoginFailureException
import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailCreatorFixture
import com.ttasjwi.board.system.member.domain.service.fixture.PasswordManagerFixture
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
            emailCreator = EmailCreatorFixture(),
            passwordManager = PasswordManagerFixture(),
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
        assertThat(command.email.value).isEqualTo(email)
        assertThat(command.rawPassword.value).isEqualTo(password)
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

    @Test
    @DisplayName("이메일 포맷이 유효하지 않으면 로그인 실패 예외가 발생한다")
    fun testInvalidEmailFormat() {
        val request = LoginRequest(email = EmailCreatorFixture.ERROR_EMAIL, password = "1234")

        val exception = assertThrows<LoginFailureException> { commandMapper.mapToCommand(request) }

        assertThat(exception.debugMessage).isEqualTo("로그인 실패 - 이메일 포맷이 유효하지 않음")
    }

    @Test
    @DisplayName("패스워드 포맷이 유효하지 않으면 로그인 실패 예외가 발생한다")
    fun testInvalidPasswordFormat() {
        val request = LoginRequest(email = "ttasjwi@gmail.com", password = PasswordManagerFixture.ERROR_PASSWORD)

        val exception = assertThrows<LoginFailureException> { commandMapper.mapToCommand(request) }

        assertThat(exception.debugMessage).isEqualTo("로그인 실패 - 패스워드 포맷이 유효하지 않음")
    }
}
