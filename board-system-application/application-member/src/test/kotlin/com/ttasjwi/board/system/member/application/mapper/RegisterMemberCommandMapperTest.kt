package com.ttasjwi.board.system.member.application.mapper

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.NullArgumentException
import com.ttasjwi.board.system.core.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.core.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.member.domain.model.fixture.emailFixture
import com.ttasjwi.board.system.member.domain.model.fixture.nicknameFixture
import com.ttasjwi.board.system.member.domain.model.fixture.usernameFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailCreatorFixture
import com.ttasjwi.board.system.member.domain.service.fixture.NicknameCreatorFixture
import com.ttasjwi.board.system.member.domain.service.fixture.PasswordManagerFixture
import com.ttasjwi.board.system.member.domain.service.fixture.UsernameCreatorFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.ZonedDateTime

@DisplayName("RegisterMemberCommandMapper 테스트")
class RegisterMemberCommandMapperTest {

    private lateinit var commandMapper: RegisterMemberCommandMapper
    private lateinit var currentTime: ZonedDateTime

    @BeforeEach
    fun setup() {
        val timeManager = TimeManagerFixture()
        currentTime = timeFixture(minute = 6)
        timeManager.changeCurrentTime(currentTime)

        commandMapper = RegisterMemberCommandMapper(
            emailCreator = EmailCreatorFixture(),
            passwordManager = PasswordManagerFixture(),
            usernameCreator = UsernameCreatorFixture(),
            nicknameCreator = NicknameCreatorFixture(),
            timeManager = timeManager,
        )
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
        assertThat(command.email).isEqualTo(emailFixture(request.email!!))
        assertThat(command.rawPassword.value).isEqualTo(request.password)
        assertThat(command.username).isEqualTo(usernameFixture(request.username!!))
        assertThat(command.nickname).isEqualTo(nicknameFixture(request.nickname!!))
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
            email = EmailCreatorFixture.ERROR_EMAIL,
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
            password = PasswordManagerFixture.ERROR_PASSWORD,
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
            username = UsernameCreatorFixture.ERROR_USERNAME,
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
            nickname = NicknameCreatorFixture.ERROR_NICKNAME,
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
