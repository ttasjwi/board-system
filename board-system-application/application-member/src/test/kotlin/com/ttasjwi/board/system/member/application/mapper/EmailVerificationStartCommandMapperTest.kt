package com.ttasjwi.board.system.member.application.mapper

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.NullArgumentException
import com.ttasjwi.board.system.core.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.core.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartRequest
import com.ttasjwi.board.system.member.domain.model.fixture.emailFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailCreatorFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import java.util.*

@DisplayName("EmailVerificationStartCommandMapper: EmailVerificationStartRequest 를 EmailVerificationStartCommand 로 변환한다")
class EmailVerificationStartCommandMapperTest {

    private lateinit var commandMapper: EmailVerificationStartCommandMapper
    private lateinit var emailCreatorFixture: EmailCreatorFixture
    private lateinit var localeManagerFixture: LocaleManagerFixture
    private lateinit var timeManagerFixture: TimeManagerFixture

    @BeforeEach
    fun setup() {
        emailCreatorFixture = EmailCreatorFixture()
        localeManagerFixture = LocaleManagerFixture()
        timeManagerFixture = TimeManagerFixture()
        commandMapper = EmailVerificationStartCommandMapper(
            emailCreator = emailCreatorFixture,
            timeManager = timeManagerFixture,
            localeManager = localeManagerFixture,
        )
    }

    @Nested
    @DisplayName("mapToCommand: 사용자 요청을 애플리케이션 명령으로 변환한다.")
    inner class MapToCommand {

        @Test
        @DisplayName("이메일이 누락되면 예외가 발생한다")
        fun testNullEmail() {
            val request = EmailVerificationStartRequest(email = null)

            val exception = assertThrows<NullArgumentException> { commandMapper.mapToCommand(request) }
            assertThat(exception.source).isEqualTo("email")
        }

        @Test
        @DisplayName("이메일이 형식이 유효하지 않으면 예외가 발생한다")
        fun testInvalidFormatEmail() {
            val request = EmailVerificationStartRequest(email = EmailCreatorFixture.ERROR_EMAIL)

            val exception = assertThrows<CustomException> { commandMapper.mapToCommand(request) }
            assertThat(exception.debugMessage).isEqualTo("이메일 포맷 예외 - 픽스쳐")
        }

        @Test
        @DisplayName("이메일 포맷이 유효할 경우 -> 성공")
        fun testSuccess() {
            localeManagerFixture.changeLocale(Locale.ENGLISH)
            timeManagerFixture.changeCurrentTime(timeFixture(minute=3))

            val request = EmailVerificationStartRequest(email = "hello@gmail.com")

            val command = commandMapper.mapToCommand(request)

            assertThat(command).isNotNull
            assertThat(command.email).isEqualTo(emailFixture("hello@gmail.com"))
            assertThat(command.locale).isEqualTo(Locale.ENGLISH)
            assertThat(command.currenTime).isEqualTo(timeFixture(minute=3))
        }
    }
}
