package com.ttasjwi.board.system.member.domain

import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.mapper.EmailVerificationCommandMapper
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.member.domain.port.fixture.EmailFormatValidatePortFixture
import com.ttasjwi.board.system.member.domain.port.fixture.EmailVerificationPersistencePortFixture
import com.ttasjwi.board.system.member.domain.processor.EmailVerificationProcessor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationApplicationService 테스트")
class EmailVerificationApplicationServiceTest {

    private lateinit var emailVerificationApplicationService: EmailVerificationApplicationService
    private lateinit var timeManagerFixture: TimeManagerFixture
    private lateinit var emailVerificationPersistencePortFixture: EmailVerificationPersistencePortFixture

    @BeforeEach
    fun setup() {
        timeManagerFixture = TimeManagerFixture()
        emailVerificationPersistencePortFixture = EmailVerificationPersistencePortFixture()
        emailVerificationApplicationService = EmailVerificationApplicationService(
            commandMapper = EmailVerificationCommandMapper(
                emailFormatValidatePort = EmailFormatValidatePortFixture(),
                timeManager = timeManagerFixture
            ),
            processor = EmailVerificationProcessor(
                emailVerificationPersistencePort = emailVerificationPersistencePortFixture,
            ),
        )
    }

    @Test
    @DisplayName("이메일 인증을 처리하고 그 결과를 반환한다.")
    fun test() {
        // given
        val savedEmailVerification = emailVerificationFixtureNotVerified(
            email = "hello@gmail.com",
            code = "1234",
            codeCreatedAt = appDateTimeFixture(minute = 0),
            codeExpiresAt = appDateTimeFixture(minute = 5),
        )
        emailVerificationPersistencePortFixture.save(savedEmailVerification, savedEmailVerification.codeExpiresAt)
        timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 3))

        val request = EmailVerificationRequest(
            email = savedEmailVerification.email,
            code = savedEmailVerification.code
        )

        // when
        val response = emailVerificationApplicationService.emailVerification(request)

        // then
        assertThat(response.email).isEqualTo(request.email)
        assertThat(response.verificationExpiresAt).isEqualTo(
            appDateTimeFixture(minute = 3).plusMinutes(EmailVerification.VERIFICATION_VALIDITY_MINUTE).toZonedDateTime()
        )
    }
}
