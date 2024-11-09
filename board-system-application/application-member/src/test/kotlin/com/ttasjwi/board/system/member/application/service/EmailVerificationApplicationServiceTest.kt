package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.application.fixture.TransactionRunnerFixture
import com.ttasjwi.board.system.core.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.application.mapper.EmailVerificationCommandMapper
import com.ttasjwi.board.system.member.application.processor.EmailVerificationProcessor
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.member.domain.service.fixture.EmailCreatorFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationEventCreatorFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationHandlerFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationStorageFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationApplicationService 테스트")
class EmailVerificationApplicationServiceTest {

    private lateinit var emailVerificationApplicationService: EmailVerificationApplicationService
    private lateinit var timeManagerFixture: TimeManagerFixture
    private lateinit var emailVerificationStorageFixture: EmailVerificationStorageFixture

    @BeforeEach
    fun setup() {
        timeManagerFixture = TimeManagerFixture()
        emailVerificationStorageFixture = EmailVerificationStorageFixture()
        emailVerificationApplicationService = EmailVerificationApplicationService(
            commandMapper = EmailVerificationCommandMapper(
                emailCreator = EmailCreatorFixture(),
                timeManager = timeManagerFixture
            ),
            processor = EmailVerificationProcessor(
                emailVerificationFinder = emailVerificationStorageFixture,
                emailVerificationHandler = EmailVerificationHandlerFixture(),
                emailVerificationAppender = emailVerificationStorageFixture,
                emailVerificationEventCreator = EmailVerificationEventCreatorFixture()
            ),
            transactionRunner = TransactionRunnerFixture()
        )
    }

    @Test
    @DisplayName("이메일 인증을 처리하고 그 결과를 반환한다.")
    fun test() {
        // given
        val savedEmailVerification = emailVerificationFixtureNotVerified(
            email = "hello@gmail.com",
            code = "1234",
            codeCreatedAt = timeFixture(minute=0),
            codeExpiresAt = timeFixture(minute=5),
        )
        emailVerificationStorageFixture.append(savedEmailVerification, savedEmailVerification.codeExpiresAt)
        timeManagerFixture.changeCurrentTime(timeFixture(minute = 3))

        val request = EmailVerificationRequest(
            email = savedEmailVerification.email.value,
            code = savedEmailVerification.code
        )

        // when
        val result = emailVerificationApplicationService.emailVerification(request)

        // then
        assertThat(result.email).isEqualTo(request.email)
        assertThat(result.verificationExpiresAt).isEqualTo(timeFixture(minute=3).plusMinutes(EmailVerificationHandlerFixture.VERIFICATION_VALIDITY_MINUTE))
    }

}
