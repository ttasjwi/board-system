package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.application.fixture.TransactionRunnerFixture
import com.ttasjwi.board.system.core.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.core.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.application.mapper.EmailVerificationStartCommandMapper
import com.ttasjwi.board.system.member.application.processor.EmailVerificationStartProcessor
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartRequest
import com.ttasjwi.board.system.member.domain.service.fixture.*
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
                emailCreator = EmailCreatorFixture(),
                localeManager = LocaleManagerFixture(),
                timeManager = timeManagerFixture
            ),
            processor = EmailVerificationStartProcessor(
                emailVerificationCreator = EmailVerificationCreatorFixture(),
                emailVerificationAppender = EmailVerificationStorageFixture(),
                emailVerificationEventCreator = EmailVerificationEventCreatorFixture()
            ),
            transactionRunner = TransactionRunnerFixture(),
            eventPublisher = EmailVerificationStartedEventPublisherFixture()
        )
    }

    @Test
    @DisplayName("이메일 인증 시작절차에 들어가고, 그 결과를 반환한다.")
    fun test() {
        timeManagerFixture.changeCurrentTime(timeFixture(minute = 3))
        val request = EmailVerificationStartRequest("hello@gmail.com")

        val result = applicationService.startEmailVerification(request)

        assertThat(result.email).isEqualTo("hello@gmail.com")
        assertThat(result.codeExpiresAt).isEqualTo(timeFixture(minute = 8))
    }
}
