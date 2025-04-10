package com.ttasjwi.board.system.member.domain.processor

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.dto.EmailVerificationCommand
import com.ttasjwi.board.system.member.domain.exception.EmailVerificationNotFoundException
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.member.domain.port.fixture.EmailVerificationPersistencePortFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("EmailVerificationProcessor: 이메일 인증명령을 처리하는 애플리케이션 처리자")
class EmailVerificationProcessorTest {

    private lateinit var emailVerificationProcessor: EmailVerificationProcessor
    private lateinit var emailVerificationPersistencePortFixture: EmailVerificationPersistencePortFixture

    @BeforeEach
    fun setup() {
        emailVerificationPersistencePortFixture = EmailVerificationPersistencePortFixture()
        emailVerificationProcessor = EmailVerificationProcessor(
            emailVerificationPersistencePort = emailVerificationPersistencePortFixture
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
        emailVerificationPersistencePortFixture.save(savedEmailVerification, savedEmailVerification.codeExpiresAt)

        val command = EmailVerificationCommand(
            email = savedEmailVerification.email,
            code = savedEmailVerification.code,
            currentTime = appDateTimeFixture(minute = 3)
        )

        // when
        val verifiedEmailVerification = emailVerificationProcessor.verify(command)

        // then
        val findEmailVerification =
            emailVerificationPersistencePortFixture.findByEmailOrNull(savedEmailVerification.email)!!

        assertThat(verifiedEmailVerification.email).isEqualTo(command.email)
        assertThat(verifiedEmailVerification.code).isEqualTo(command.code)
        assertThat(verifiedEmailVerification.codeCreatedAt).isEqualTo(savedEmailVerification.codeCreatedAt)
        assertThat(verifiedEmailVerification.codeExpiresAt).isEqualTo(savedEmailVerification.codeExpiresAt)
        assertThat(verifiedEmailVerification.verifiedAt).isEqualTo(command.currentTime)
        assertThat(verifiedEmailVerification.verificationExpiresAt).isEqualTo(
            command.currentTime.plusMinutes(
                EmailVerification.VERIFICATION_VALIDITY_MINUTE
            )
        )
        assertThat(findEmailVerification.verifiedAt).isEqualTo(verifiedEmailVerification.verifiedAt)
        assertThat(findEmailVerification.verificationExpiresAt).isEqualTo(verifiedEmailVerification.verificationExpiresAt)
    }
}
