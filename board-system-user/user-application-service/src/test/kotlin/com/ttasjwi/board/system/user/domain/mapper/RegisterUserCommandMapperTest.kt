package com.ttasjwi.board.system.user.domain.mapper

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.RegisterMemberRequest
import com.ttasjwi.board.system.user.domain.policy.fixture.NicknamePolicyFixture
import com.ttasjwi.board.system.user.domain.policy.fixture.PasswordPolicyFixture
import com.ttasjwi.board.system.user.domain.policy.fixture.UsernamePolicyFixture
import com.ttasjwi.board.system.user.domain.port.fixture.EmailFormatValidatePortFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("RegisterMemberCommandMapper 테스트")
class RegisterUserCommandMapperTest {

    private lateinit var commandMapper: RegisterMemberCommandMapper
    private lateinit var currentTime: AppDateTime

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        currentTime = appDateTimeFixture(minute = 6)
        container.timeManagerFixture.changeCurrentTime(currentTime)
        commandMapper = container.registerMemberCommandMapper
    }

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val request = RegisterMemberRequest(
            email = "hello@gmail.com",
            password = "1234",
            username = "ttasjwi",
            nickname = "땃쥐",
        )

        // when
        val command = commandMapper.mapToCommand(request)

        // then
        assertThat(command.email).isEqualTo(request.email)
        assertThat(command.rawPassword).isEqualTo(request.password)
        assertThat(command.username).isEqualTo(request.username!!)
        assertThat(command.nickname).isEqualTo(request.nickname!!)
        assertThat(command.currentTime).isEqualTo(currentTime)
    }

    @Test
    @DisplayName("이메일이 누락되면 예외 발생")
    fun testEmailNull() {
        val request = RegisterMemberRequest(
            email = null,
            password = "1234",
            username = "ttasjwi",
            nickname = "땃쥐",
        )

        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("email")
    }

    @Test
    @DisplayName("패스워드 누락되면 예외 발생")
    fun testPasswordNull() {
        val request = RegisterMemberRequest(
            email = "hello@gmail.com",
            password = null,
            username = "ttasjwi",
            nickname = "땃쥐",
        )

        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("password")
    }

    @Test
    @DisplayName("사용자아이디(username) 누락되면 예외 발생")
    fun testUsernameNull() {
        val request = RegisterMemberRequest(
            email = "hello@gmail.com",
            password = "1234",
            username = null,
            nickname = "땃쥐",
        )

        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("username")
    }

    @Test
    @DisplayName("닉네임 누락되면 예외 발생")
    fun testNicknameNull() {
        val request = RegisterMemberRequest(
            email = "hello@gmail.com",
            password = "1234",
            username = "ttasjwi",
            nickname = null,
        )

        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("nickname")
    }

    @Test
    @DisplayName("이메일 포맷이 유효하지 않을 때 예외 발생")
    fun testInvalidEmailFormat() {
        val request = RegisterMemberRequest(
            email = EmailFormatValidatePortFixture.ERROR_EMAIL,
            password = "1234",
            username = "ttasjwi",
            nickname = "땃쥐",
        )

        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(CustomException::class.java)
        assertThat(exception.source).isEqualTo("email")
    }

    @Test
    @DisplayName("패스워드 포맷이 유효하지 않을 때 예외 발생")
    fun testInvalidPasswordFormat() {
        val request = RegisterMemberRequest(
            email = "hello@gmail.com",
            password = PasswordPolicyFixture.ERROR_PASSWORD,
            username = "ttasjwi",
            nickname = "땃쥐",
        )

        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(CustomException::class.java)
        assertThat(exception.source).isEqualTo("password")
    }

    @Test
    @DisplayName("사용자아이디(username) 포맷이 유효하지 않을 때 예외 발생")
    fun testInvalidUsernameFormat() {
        val request = RegisterMemberRequest(
            email = "hello@gmail.com",
            password = "1234",
            username = UsernamePolicyFixture.ERROR_USERNAME,
            nickname = "땃쥐",
        )

        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(CustomException::class.java)
        assertThat(exception.source).isEqualTo("username")
    }

    @Test
    @DisplayName("닉네임 포맷이 유효하지 않을 때 예외 발생")
    fun testInvalidNicknameFormat() {
        val request = RegisterMemberRequest(
            email = "hello@gmail.com",
            password = "1234",
            username = "ttasjwi",
            nickname = NicknamePolicyFixture.ERROR_NICKNAME,
        )

        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            commandMapper.mapToCommand(request)
        }
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(CustomException::class.java)
        assertThat(exception.source).isEqualTo("nickname")
    }
}
