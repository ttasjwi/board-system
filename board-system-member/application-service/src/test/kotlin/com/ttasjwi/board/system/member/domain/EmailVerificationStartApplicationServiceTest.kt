package com.ttasjwi.board.system.member.domain

import com.ttasjwi.board.system.common.event.fixture.EventPublishPortFixture
import com.ttasjwi.board.system.common.locale.fixture.LocaleResolverFixture
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.mapper.EmailVerificationStartCommandMapper
import com.ttasjwi.board.system.member.domain.port.fixture.EmailFormatValidatePortFixture
import com.ttasjwi.board.system.member.domain.port.fixture.EmailVerificationPersistencePortFixture
import com.ttasjwi.board.system.member.domain.processor.EmailVerificationStartProcessor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationStartApplicationService: 이메일 인증 시작을 수행하는 애플리케이션 서비스")
class EmailVerificationStartApplicationServiceTest {

    private lateinit var applicationService: EmailVerificationStartApplicationService
    private lateinit var timeManagerFixture: TimeManagerFixture

    @BeforeEach
    fun setup() {
        timeManagerFixture = TimeManagerFixture()
        applicationService = EmailVerificationStartApplicationService(
            commandMapper = EmailVerificationStartCommandMapper(
                emailFormatValidatePort = EmailFormatValidatePortFixture(),
                localeResolver = LocaleResolverFixture(),
                timeManager = timeManagerFixture
            ),
            processor = EmailVerificationStartProcessor(
                emailVerificationPersistencePort = EmailVerificationPersistencePortFixture(),
                eventPublishPort = EventPublishPortFixture(),
            ),
        )
    }

    @Test
    @DisplayName("이메일 인증 시작절차에 들어가고, 그 결과를 반환한다.")
    fun test() {
        timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 3))
        val request = EmailVerificationStartRequest("hello@gmail.com")

        val response = applicationService.startEmailVerification(request)

        assertThat(response.email).isEqualTo("hello@gmail.com")
        assertThat(response.codeExpiresAt).isEqualTo(appDateTimeFixture(minute = 8).toZonedDateTime())
    }
}
