package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.global.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.global.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.application.mapper.EmailVerificationStartCommandMapper
import com.ttasjwi.board.system.member.application.processor.EmailVerificationStartProcessor
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartRequest
import com.ttasjwi.board.system.member.domain.service.fixture.EmailManagerFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationCreatorFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationEventCreatorFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationStartedEventPublisherFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationStorageFixture
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
                emailManager = EmailManagerFixture(),
                localeManager = LocaleManagerFixture(),
                timeManager = timeManagerFixture
            ),
            processor = EmailVerificationStartProcessor(
                emailVerificationCreator = EmailVerificationCreatorFixture(),
                emailVerificationAppender = EmailVerificationStorageFixture(),
                emailVerificationEventCreator = EmailVerificationEventCreatorFixture()
            ),
            eventPublisher = EmailVerificationStartedEventPublisherFixture()
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
