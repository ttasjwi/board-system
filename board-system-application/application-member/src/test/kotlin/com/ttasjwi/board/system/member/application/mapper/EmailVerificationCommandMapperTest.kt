package com.ttasjwi.board.system.member.application.mapper

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus
import com.ttasjwi.board.system.core.exception.NullArgumentException
import com.ttasjwi.board.system.core.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.core.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.application.dto.EmailVerificationCommand
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.member.domain.exception.InvalidEmailFormatException
import com.ttasjwi.board.system.member.domain.model.fixture.emailFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailCreatorFixture
import jakarta.xml.bind.ValidationException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("EmailVerificationCommandMapper 테스트")
class EmailVerificationCommandMapperTest {

    private lateinit var emailVerificationCommandMapper: EmailVerificationCommandMapper
    private lateinit var timeManagerFixture: TimeManagerFixture

    @BeforeEach
    fun setup() {
        timeManagerFixture = TimeManagerFixture()
        emailVerificationCommandMapper = EmailVerificationCommandMapper(
            emailCreator = EmailCreatorFixture(),
            timeManager = timeManagerFixture
        )
    }


    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        timeManagerFixture.changeCurrentTime(timeFixture(minute = 3))
        val email = "hello@gmail.com"
        val code = "1234"

        val request = EmailVerificationRequest(email, code)

        val command = emailVerificationCommandMapper.mapToCommand(request)

        assertThat(command.email).isEqualTo(emailFixture(email))
        assertThat(command.code).isEqualTo(code)
        assertThat(command.currentTime).isEqualTo(timeFixture(minute = 3))
    }

    @Test
    @DisplayName("이메일 누락 시 예외 발생")
    fun testEmailNull() {
        val code = "1234"
        val request = EmailVerificationRequest(null, code)


        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            emailVerificationCommandMapper.mapToCommand(request)
        }
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("email")
    }

    @Test
    @DisplayName("이메일 포맷이 유효하지 않을 때 예외 발생")
    fun testInvalidEmailFormat() {
        val email = EmailCreatorFixture.ERROR_EMAIL
        val code = "1234"
        val request = EmailVerificationRequest(email, code)

        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            emailVerificationCommandMapper.mapToCommand(request)
        }
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(CustomException::class.java)
        assertThat(exception.source).isEqualTo("email")
    }

    @Test
    @DisplayName("code 누락 시 예외 발생")
    fun testCodeNull() {
        val email = "hello@gmail.com"
        val request = EmailVerificationRequest(email, null)

        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            emailVerificationCommandMapper.mapToCommand(request)
        }
        val exceptions = exceptionCollector.getExceptions()
        val exception = exceptions.first()

        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exception).isInstanceOf(NullArgumentException::class.java)
        assertThat(exception.source).isEqualTo("code")
    }
}
