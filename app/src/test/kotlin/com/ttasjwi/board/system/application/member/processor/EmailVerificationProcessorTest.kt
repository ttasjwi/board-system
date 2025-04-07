package com.ttasjwi.board.system.application.member.processor

import com.ttasjwi.board.system.application.member.dto.EmailVerificationCommand
import com.ttasjwi.board.system.application.member.exception.EmailVerificationNotFoundException
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationEventCreatorFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationHandlerFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationStorageFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("EmailVerificationProcessor: 이메일 인증명령을 처리하는 애플리케이션 처리자")
class EmailVerificationProcessorTest {

    private lateinit var emailVerificationProcessor: EmailVerificationProcessor
    private lateinit var emailVerificationStorageFixture: EmailVerificationStorageFixture
    private lateinit var emailVerificationHandlerFixture: EmailVerificationHandlerFixture
    private lateinit var emailVerificationEventCreatorFixture: EmailVerificationEventCreatorFixture

    @BeforeEach
    fun setup() {
        emailVerificationStorageFixture = EmailVerificationStorageFixture()
        emailVerificationHandlerFixture = EmailVerificationHandlerFixture()
        emailVerificationEventCreatorFixture = EmailVerificationEventCreatorFixture()
        emailVerificationProcessor = EmailVerificationProcessor(
            emailVerificationFinder = emailVerificationStorageFixture,
            emailVerificationHandler = emailVerificationHandlerFixture,
            emailVerificationAppender = emailVerificationStorageFixture,
            emailVerificationEventCreator = emailVerificationEventCreatorFixture
        )
    }

    @Test
    @DisplayName("명령의 이메일에 대응하는 이메일 인증을 조회하지 못 했다면, 예외가 발생한다")
    fun testNotFound() {
        val command = EmailVerificationCommand(
            email = "hello@gmail.com",
            code = "1234",
            currentTime = appDateTimeFixture(minute = 3)
        )
        assertThrows<EmailVerificationNotFoundException> {
            emailVerificationProcessor.verify(command)
        }
    }

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val savedEmailVerification = emailVerificationFixtureNotVerified(
            email = "hello@gmail.com",
            code = "1234",
            codeCreatedAt = appDateTimeFixture(minute = 0),
            codeExpiresAt = appDateTimeFixture(minute = 5),
        )
        emailVerificationStorageFixture.append(savedEmailVerification, savedEmailVerification.codeExpiresAt)

        val command = EmailVerificationCommand(
            email = savedEmailVerification.email,
            code = savedEmailVerification.code,
            currentTime = appDateTimeFixture(minute = 3)
        )

        // when
        val event = emailVerificationProcessor.verify(command)

        // then
        val data = event.data
        val findEmailVerification = emailVerificationStorageFixture.findByEmailOrNull(savedEmailVerification.email)!!

        assertThat(event.occurredAt).isEqualTo(command.currentTime)
        assertThat(data.email).isEqualTo(savedEmailVerification.email)
        assertThat(data.verifiedAt).isEqualTo(command.currentTime)
        assertThat(data.verificationExpiresAt).isEqualTo(command.currentTime.plusMinutes(EmailVerificationHandlerFixture.VERIFICATION_VALIDITY_MINUTE))
        assertThat(findEmailVerification.verifiedAt).isEqualTo(data.verifiedAt)
        assertThat(findEmailVerification.verificationExpiresAt).isEqualTo(data.verificationExpiresAt)
    }
}
