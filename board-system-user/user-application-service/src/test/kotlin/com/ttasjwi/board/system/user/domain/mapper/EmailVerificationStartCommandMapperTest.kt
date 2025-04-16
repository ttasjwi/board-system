package com.ttasjwi.board.system.user.domain.mapper

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.locale.fixture.LocaleResolverFixture
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.EmailVerificationStartRequest
import com.ttasjwi.board.system.user.domain.port.fixture.EmailFormatValidatePortFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import java.util.*

@DisplayName("EmailVerificationStartCommandMapper: EmailVerificationStartRequest 를 EmailVerificationStartCommand 로 변환한다")
class EmailVerificationStartCommandMapperTest {

    private lateinit var commandMapper: EmailVerificationStartCommandMapper
    private lateinit var localeResolverFixture: LocaleResolverFixture
    private lateinit var timeManagerFixture: TimeManagerFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        localeResolverFixture = container.localeResolverFixture
        timeManagerFixture = container.timeManagerFixture
        commandMapper = container.emailVerificationStartCommandMapper
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
            val request = EmailVerificationStartRequest(email = EmailFormatValidatePortFixture.ERROR_EMAIL)

            val exception = assertThrows<CustomException> { commandMapper.mapToCommand(request) }
            assertThat(exception.debugMessage).isEqualTo("이메일 포맷 예외 - 픽스쳐")
        }

        @Test
        @DisplayName("이메일 포맷이 유효할 경우 -> 성공")
        fun testSuccess() {
            localeResolverFixture.changeLocale(Locale.ENGLISH)
            timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 3))

            val request = EmailVerificationStartRequest(email = "hello@gmail.com")

            val command = commandMapper.mapToCommand(request)

            assertThat(command).isNotNull
            assertThat(command.email).isEqualTo(request.email)
            assertThat(command.locale).isEqualTo(Locale.ENGLISH)
            assertThat(command.currenTime).isEqualTo(appDateTimeFixture(minute = 3))
        }
    }
}
